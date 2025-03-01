package raisetech.StudentManagement.Repository;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@MybatisTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository sut;

    @Test
    void 受講生の全件検索が行えること() {
        List<Student> actual = sut.search();
        assertThat(actual.size()).isEqualTo(5);
    }

    @Test
    void 受講生の登録が行えること() {
        Student student = new Student();


        student.setName("佐藤一郎");
        student.setFurigana("サトウイチロウ");
        student.setNickName("いっちゃん");
        student.setEmailAddress("satou@example.com");
        student.setAddress("東京");
        student.setAge(25);
        student.setGender("男性");
        student.setRemark("");
        student.setDeleted(false);

        sut.registerStudent(student);

        List<Student> actual = sut.search();

        assertThat(actual.size()).isEqualTo(6);
    }


    @Test
    void 受講生IDで検索できること() {

        // 受講生を検索
        Student student = sut.searchStudent(1);

        // 結果が期待通りかを確認
        assertThat(student).isNotNull();
        assertThat(student.getId()).isEqualTo(1); // IDが一致するか
        assertThat(student.getName()).isEqualTo("山田太郎"); // 名前が一致するか
        assertThat(student.getEmailAddress()).isEqualTo("taro@example.com"); // メールアドレスが一致するか
    }

    @Test
    void 存在しない受講生IDで検索した場合はnullが返ること() {
        // 存在しないIDで検索
        int nonExistentStudentId = 999;

        // 受講生を検索
        Student student = sut.searchStudent(nonExistentStudentId);

        // 結果がnullであることを確認
        assertThat(student).isNull();
    }


    @Test
    void 受講生コース情報の登録が行えること() {
        // 新規受講生コース情報を作成
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudentId(1);  // 既存の受講生IDを使用（IDが存在することを前提）
        studentCourse.setCourse("Java基礎");
        studentCourse.setEnrollmentStartDate(LocalDateTime.of(2024, 4, 1, 0, 0, 0, 0));
        studentCourse.setEnrollmentEndDate(LocalDateTime.of(2024, 9, 30, 23, 59, 59, 0));

        // 登録前の受講生コース数を取得
        List<StudentCourse> beforeCourses = sut.findCoursesByStudentId(1);
        int beforeCount = beforeCourses.size();

        // 受講生コース情報を登録
        sut.registerStudentCourse(studentCourse);

        // 登録後の受講生コース数を取得
        List<StudentCourse> afterCourses = sut.findCoursesByStudentId(1);
        int afterCount = afterCourses.size();

        // コース数が1つ増えていることを確認
        assertThat(afterCount).isEqualTo(beforeCount + 1);

        // 登録されたコース情報を取得
        StudentCourse registeredCourse = afterCourses.get(afterCount - 1);

        // 登録したコース名と一致することを確認
        assertThat(registeredCourse.getCourse()).isEqualTo("Java基礎");
        // 登録したコース開始日と終了日が一致することを確認
        assertThat(registeredCourse.getEnrollmentStartDate()).isEqualTo(LocalDateTime.of(2024, 4, 1, 0, 0, 0, 0));
        assertThat(registeredCourse.getEnrollmentEndDate()).isEqualTo(LocalDateTime.of(2024, 9, 30, 23, 59, 59, 0));
    }


    @Test
    void 存在しない学生IDでコースを検索した場合_空リストが返ること() {
        List<StudentCourse> courses = sut.findCoursesByStudentId(9999); // 存在しないID
        assertThat(courses).isEmpty(); // 空リストであることを確認
    }
    @Test
    void 受講生情報を更新できること() {

        Student student = sut.searchStudent(1);


        // データを更新
        student.setName("更新 太郎");
        student.setEmailAddress("updated@example.com");

        // 受講生情報を更新
        sut.updateStudent(student);

        // 更新後のデータを取得し検証
        Student updatedStudent = sut.searchStudent(1);
        assertThat(updatedStudent.getName()).isEqualTo("更新 太郎");
        assertThat(updatedStudent.getEmailAddress()).isEqualTo("updated@example.com");
    }


    @Test
    void 受講生コース情報を更新できること() {
        // 既存のコース情報を取得
        List<StudentCourse> courses = sut.findCoursesByStudentId(1);
        StudentCourse course = courses.get(0); // 最初のコース情報を選択

        // コース情報を更新
        course.setCourse("Java応用");
        sut.updateStudentCourse(course);

        // 更新後のコース情報を取得し、確認
        List<StudentCourse> updatedCourses = sut.findCoursesByStudentId(1);
        StudentCourse updatedCourse = updatedCourses.get(0);
        assertThat(updatedCourse.getCourse()).isEqualTo("Java応用");
    }
    @Test
    void 受講生のコース情報の全件検索が行えること() {
        List<StudentCourse> courses = sut.searchStudentCoursesList();
        assertThat(courses.size()).isGreaterThan(0); // 少なくとも1つ以上のコース情報があることを確認
    }


}



