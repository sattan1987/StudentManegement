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
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService service;

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void 受講生詳細の一覧検索が実行できて空のリストが帰ってくること() throws Exception {

        when(service.searchStudentList()).thenReturn(List.of(new StudentDetail()));
        mockMvc.perform(get("/studentList"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"student\":null,\"studentCourseList\":null}]"));

        verify(service, times(1)).searchStudentList();

    }

    @Test
    void 受講生詳細の受講生で入力チェックに異常がないこと() {

        Student student = new Student();

        student.setId(80);
        student.setName("秋元佐智");
        student.setFurigana("アキモトサチ");
        student.setNickName("さっちゃん");
        student.setEmailAddress("aaa.com");
        student.setAddress("宇都宮市");
        student.setAge(38);

        Set<ConstraintViolation<Object>> violations = validator.validate(student);
        assertThat(violations.size()).isEqualTo(0);


    }

    @Test
    void 受講生詳細の受講生でIDに範囲外の数字を用いた際に入力チェックにかかること() {

        Student student = new Student();

        student.setId(1000);
        student.setName("秋元佐智");
        student.setFurigana("アキモトサチ");
        student.setNickName("さっちゃん");
        student.setEmailAddress("aaa.com");
        student.setAddress("宇都宮市");
        student.setAge(38);

        Set<ConstraintViolation<Object>> violations = validator.validate(student);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations).extracting("message").containsOnly("数字のみ入力するようにしてください");


    }

    @Test
    void getStudentAPIが正常にデータを取得できること() throws Exception {
        // モックデータの作成
        Student student = new Student();
        student.setId(1);  // IDを設定
        student.setName("秋元佐智");
        student.setFurigana("アキモトサチ");
        student.setNickName("さっちゃん");
        student.setEmailAddress("aaa@example.com");
        student.setAddress("宇都宮市");
        student.setAge(38);

        // StudentDetail に Student をセット
        StudentDetail studentDetail = new StudentDetail();
        studentDetail.setStudent(student);

        // サービスの動作をモック化
        when(service.getStudentDetailById(1)).thenReturn(studentDetail);

        // APIリクエストを送信してレスポンスを検証
        mockMvc.perform(get("/student/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.student.id").value(1))
                .andExpect(jsonPath("$.student.name").value("秋元佐智"))
                .andExpect(jsonPath("$.student.furigana").value("アキモトサチ"))
                .andExpect(jsonPath("$.student.nickName").value("さっちゃん"))
                .andExpect(jsonPath("$.student.emailAddress").value("aaa@example.com"))
                .andExpect(jsonPath("$.student.address").value("宇都宮市"))
                .andExpect(jsonPath("$.student.age").value(38));

        // サービスメソッドが1回呼び出されたことを検証
        verify(service, times(1)).getStudentDetailById(1);
    }
}