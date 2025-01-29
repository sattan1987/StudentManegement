package raisetech.StudentManagement.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.StudentManagement.Repository.StudentRepository;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository repository;

    @Mock
    private StudentConverter converter;
    private StudentService sut;
    private StudentServiceTest studentService;
    private StudentDetail studentDetail;

    @BeforeEach
    void before() {
        sut = new StudentService(repository, converter);
    }

    @Test
    void 受講生一覧検索_リポジトリとコンバーターの処理が適切に呼び出せていること() {
        // 事前準備
        List<Student> studentList = new ArrayList<>();
        List<StudentCourse> studentCourseList = new ArrayList<>();

        when(repository.search()).thenReturn(studentList);
        when(repository.findCoursesByStudentId()).thenReturn(studentCourseList); // メソッド名を実装と一致させる

        // 実行
        sut.searchStudentList();

        // 検証
        verify(repository, times(1)).search();
        verify(repository, times(1)).findCoursesByStudentId(); // 修正箇所
        verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList);


    }

    @Test
    void 受講生更新_データが入力された場合_リポジトリのメソッドが適切に呼び出されること() {
        // 事前準備
        Student student = new Student();
        student.setId(1); // IDを設定
        student.setName("名無しのゴンベ"); // 名前を設定
        student.setAge(25); // 年齢を設定

        // コースデータを設定
        List<StudentCourse> studentCourseList = new ArrayList<>();
        studentCourseList.add(new StudentCourse(1, "Mathematics")); // コース名を設定

        studentDetail = new StudentDetail(student, studentCourseList); // StudentDetailに設定

        // リポジトリの動作を設定
        doNothing().when(repository).updateStudent(any(Student.class));
        doNothing().when(repository).updateStudentCourse(any(StudentCourse.class));

        // 実行
        sut.updateStudent(studentDetail);

        // 検証
        verify(repository, times(1)).updateStudent(any(Student.class)); // Studentの更新を確認
        verify(repository, times(studentCourseList.size())).updateStudentCourse(any(StudentCourse.class)); // StudentCourseの更新を確認
    }

    @Test
    void 受講生登録_データが入力された場合_リポジトリのメソッドが適切に呼び出されること() {
        // 事前準備：Student オブジェクトの設定
        Student student = new Student();
        student.setId(1); // StudentのIDを設定
        student.setName("名無しのゴン"); // 名前を設定
        student.setAge(22); // 年齢を設定

        // StudentCourse リストの設定
        List<StudentCourse> studentCourseList = new ArrayList<>();
        LocalDateTime startDate = LocalDateTime.now();  // 開始日
        LocalDateTime endDate = LocalDateTime.now();  // 終了日

        // コースの追加（仮に "Science" コースを追加）
        StudentCourse course = new StudentCourse(1, student.getId(), "Science", startDate, endDate);
        studentCourseList.add(course);  // コースリストに追加

        // StudentDetail を作成
        StudentDetail studentDetail = new StudentDetail(student, studentCourseList);

        // リポジトリのモック設定（実際の登録処理をしない）
        doNothing().when(repository).registerStudent(any(Student.class));
        doNothing().when(repository).registerStudentCourse(any(StudentCourse.class));

        // 実行：登録メソッドの呼び出し
        sut.registerStudent(studentDetail);

        // 検証：リポジトリのメソッドが呼ばれていることを確認
        verify(repository, times(1)).registerStudent(any(Student.class)); // Student の登録を1回呼び出し
        verify(repository, times(studentCourseList.size())).registerStudentCourse(any(StudentCourse.class)); // コースの登録を1回呼び出し
    }


}


