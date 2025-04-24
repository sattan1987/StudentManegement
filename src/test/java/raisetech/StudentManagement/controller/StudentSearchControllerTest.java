package raisetech.StudentManagement.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.service.StudentSearchService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(StudentSearchController.class)
class StudentSearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentSearchService service;

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    @Test
    void 名前で学生が検索できること() throws Exception {
        Student student = new Student(1, "秋元佐智", "アキモトサチ", "さっちゃん", "aaa@example.com", "宇都宮市", 38, "女性", "", false);

        when(service.searchByName("秋元佐智")).thenReturn(List.of(student));

        mockMvc.perform(get("/students/searchByName").param("name", "秋元佐智"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("秋元佐智"));

        verify(service, times(1)).searchByName("秋元佐智");
    }



    @Test
    void 存在しない名前の生徒を検索した場合に404を返すこと() throws Exception {
        // サービスが空のリストを返す
        when(service.searchByName("UnknownStudent")).thenReturn(List.of());

        mockMvc.perform(get("/students/searchByName").param("name", "UnknownStudent"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Student not found"));

        verify(service, times(1)).searchByName("UnknownStudent");
    }

    @Test
    void コースで受講生が検索できること() throws Exception {
        // 受講生のオブジェクト作成
        Student student = new Student(1, "秋元佐智", "アキモトサチ", "さっちゃん", "aaa@example.com", "宇都宮市", 38, "女性", "", false);

        // サービスがこのコースの受講生を返すようにモックする
        when(service.searchByCourse("Java")).thenReturn(List.of(student));

        // クエリパラメータでcourseを渡してリクエストを実行
        mockMvc.perform(get("/students/searchByCourse").param("course", "Java"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("秋元佐智"));

        verify(service, times(1)).searchByCourse("Java");
    }

    @Test
    void 存在しないコースで受講生を検索した場合に404を返すこと() throws Exception {
        // サービスが空のリストを返す
        when(service.searchByCourse("UnknownCourse")).thenReturn(List.of());

        // クエリパラメータでcourseを渡してリクエストを実行
        mockMvc.perform(get("/students/searchByCourse").param("course", "UnknownCourse"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Student not found"));

        verify(service, times(1)).searchByCourse("UnknownCourse");
        // 名前とメールアドレスで受講生が検索できること
    }
    @Test
    void 住所で受講生が検索できること() throws Exception {
        Student student = new Student(1, "秋元佐智", "アキモトサチ", "さっちゃん", "aaa@example.com", "宇都宮市", 38, "女性", "", false);

        when(service.searchByAddress("宇都宮市")).thenReturn(List.of(student));

        mockMvc.perform(get("/students/searchByAddress").param("address", "宇都宮市"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("秋元佐智"))
                .andExpect(jsonPath("$[0].address").value("宇都宮市"));

        verify(service, times(1)).searchByAddress("宇都宮市");
    }

    @Test
    void 存在しない住所で受講生を検索した場合に404を返すこと() throws Exception {
        when(service.searchByAddress("不存在の住所")).thenReturn(List.of());

        mockMvc.perform(get("/students/searchByAddress").param("address", "不存在の住所"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Student not found"));

        verify(service, times(1)).searchByAddress("不存在の住所");
    }
    @Test
    void ふりがなで受講生が検索できること() throws Exception {
        Student student = new Student(1, "秋元佐智", "アキモトサチ", "さっちゃん", "aaa@example.com", "宇都宮市", 38, "女性", "", false);

        when(service.searchByFurigana("アキモトサチ")).thenReturn(List.of(student));

        mockMvc.perform(get("/students/searchByFurigana").param("furigana", "アキモトサチ"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("秋元佐智"));

        verify(service, times(1)).searchByFurigana("アキモトサチ");
    }

    @Test
    void ニックネームで受講生が検索できること() throws Exception {
        Student student = new Student(1, "秋元佐智", "アキモトサチ", "さっちゃん", "aaa@example.com", "宇都宮市", 38, "女性", "", false);

        when(service.searchByNickName("さっちゃん")).thenReturn(List.of(student));

        mockMvc.perform(get("/students/searchByNickName").param("nickName", "さっちゃん"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nickName").value("さっちゃん"));

        verify(service, times(1)).searchByNickName("さっちゃん");
    }

    @Test
    void 年齢で受講生が検索できること() throws Exception {
        Student student = new Student(1, "秋元佐智", "アキモトサチ", "さっちゃん", "aaa@example.com", "宇都宮市", 38, "女性", "", false);

        when(service.searchByAge(38)).thenReturn(List.of(student));

        mockMvc.perform(get("/students/searchByAge").param("age", "38"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].age").value(38));

        verify(service, times(1)).searchByAge(38);
    }
    @Test
    void 論理削除された受講生が検索できること() throws Exception {
        Student deletedStudent = new Student(2, "削除太郎", "サクジョタロウ", "さくちゃん", "delete@example.com", "那覇市", 40, "男性", "", true);

        when(service.searchDeletedStudents()).thenReturn(List.of(deletedStudent));

        mockMvc.perform(get("/students/deleted"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[0].name").value("削除太郎"));

        verify(service, times(1)).searchDeletedStudents();
    }

    @Test
    void コース名で受講生が検索できること() throws Exception {
        // "Java中級" のコース名に該当する受講生がいない場合
        when(service.searchByCourse("Java中級")).thenReturn(Collections.emptyList());  // メソッド名を一致させる

        mockMvc.perform(get("/students/searchByCourseName").param("courseName", "Java中級"))
                .andExpect(status().isOk())  // ステータスコード 200 OK を期待
                .andExpect(jsonPath("$").isEmpty());  // 空リストであることを確認

        // モックされたサービスが一度呼び出されたかを確認
        verify(service, times(1)).searchByCourse("Java中級");  // サービスメソッドを確認
    }

    @Test
    void 契約ステータスで受講生が検索できること() throws Exception {
        // StudentCourse のインスタンスを作成
        StudentCourse sc = new StudentCourse(1, 1001, "Java基礎",
                LocalDateTime.now().minusDays(10),
                LocalDateTime.now().plusDays(10),
                "受講中");

        // モックの返り値として設定
        when(service.searchByStatus("受講中")).thenReturn(List.of(sc));

        mockMvc.perform(get("/students/searchByStatus")
                        .param("status", "受講中"))
                .andExpect(status().isOk())  // ステータスコード 200 OK を期待
                .andExpect(jsonPath("$[0].id").value(1))  // id が 1 であることを確認
                .andExpect(jsonPath("$[0].course").value("Java基礎"));  // course が "Java基礎" であることを確認

        // モックされたサービスが一度呼び出されたかを確認
        verify(service, times(1)).searchByStatus("受講中");
    }}

