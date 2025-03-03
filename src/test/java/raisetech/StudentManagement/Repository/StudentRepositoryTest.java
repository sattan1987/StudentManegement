package raisetech.StudentManagement.Repository;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
        Student expectedStudent = new Student(null, "佐藤一郎", "サトウイチロウ", "いっちゃん", "satou@example.com", "東京", 25, "男性", "", false);
        sut.registerStudent(expectedStudent);

        List<Student> actual = sut.search();
        assertThat(actual.size()).isEqualTo(6);

        Student lastStudent = actual.get(actual.size() - 1);
        assertThat(lastStudent).isEqualTo(expectedStudent);  // インスタンス同士の比較
        assertThat(lastStudent.hashCode()).isEqualTo(expectedStudent.hashCode());
    }

    @Test
    void 受講生IDで検索できること() {
        Student expectedStudent = new Student(1, "山田太郎", null, null, "taro@example.com", null, 0, null, "", false);
        Student actualStudent = sut.searchStudent(1);
        assertThat(actualStudent).isEqualTo(expectedStudent);
        assertThat(actualStudent.hashCode()).isEqualTo(expectedStudent.hashCode());
    }

    @Test
    void 存在しない受講生IDで検索した場合はnullが返ること() {
        Student actualStudent = sut.searchStudent(999);
        assertThat(actualStudent).isNull();
    }

    @Test
    void 受講生コース情報の登録が行えること() {
        StudentCourse expectedCourse = new StudentCourse(0, 1, "Java基礎",
                LocalDateTime.of(2024, 4, 1, 0, 0),
                LocalDateTime.of(2024, 9, 30, 23, 59));

        List<StudentCourse> beforeCourses = sut.findCoursesByStudentId(1);
        int beforeCount = beforeCourses.size();

        sut.registerStudentCourse(expectedCourse);
        List<StudentCourse> afterCourses = sut.findCoursesByStudentId(1);
        int afterCount = afterCourses.size();

        assertThat(afterCount).isEqualTo(beforeCount + 1);

        StudentCourse registeredCourse = afterCourses.get(afterCount - 1);
        assertThat(registeredCourse).isEqualTo(expectedCourse);
        assertThat(registeredCourse.hashCode()).isEqualTo(expectedCourse.hashCode());
    }

    @Test
    void 存在しない学生IDでコースを検索した場合_空リストが返ること() {
        List<StudentCourse> courses = sut.findCoursesByStudentId(9999);
        assertThat(courses).isEmpty();
    }

    @Test
    void 受講生情報を更新できること() {
        Student student = sut.searchStudent(1);
        student.setName("更新 太郎");
        student.setEmailAddress("updated@example.com");

        sut.updateStudent(student);
        Student updatedStudent = sut.searchStudent(1);

        assertThat(updatedStudent).isEqualTo(student);
        assertThat(updatedStudent.hashCode()).isEqualTo(student.hashCode());
    }

    @Test
    void 受講生コース情報を更新できること() {
        List<StudentCourse> courses = sut.findCoursesByStudentId(1);
        StudentCourse course = courses.get(0);
        course.setCourse("Java応用");

        sut.updateStudentCourse(course);
        List<StudentCourse> updatedCourses = sut.findCoursesByStudentId(1);
        StudentCourse updatedCourse = updatedCourses.get(0);

        assertThat(updatedCourse).isEqualTo(course);
        assertThat(updatedCourse.hashCode()).isEqualTo(course.hashCode());
    }

    @Test
    void 受講生のコース情報の全件検索が行えること() {
        List<StudentCourse> courses = sut.searchStudentCoursesList();
        assertThat(courses.size()).isGreaterThan(0);
    }
}
