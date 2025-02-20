package raisetech.StudentManagement.controller.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudentConverterTest {

    private StudentConverter converter;
    private List<Student> students;
    private List<StudentCourse> courses;

    @BeforeEach
    void setUp() {
        // Converter のインスタンス作成
        converter = new StudentConverter();
    }

    @Test
    void 受講生が複数のコースを持つ場合_正しくマッピングされること() {
        // 受講生とコースを準備
        Student student1 = new Student(1, "Alice");
        Student student2 = new Student(2, "Bob");

        StudentCourse course1 = new StudentCourse(1, 1, "Math");
        StudentCourse course2 = new StudentCourse(2, 1, "Science");
        StudentCourse course3 = new StudentCourse(3, 2, "History");

        students = Arrays.asList(student1, student2);
        courses = Arrays.asList(course1, course2, course3);

        // メソッド実行
        List<StudentDetail> result = converter.convertStudentDetails(students, courses);

        // 検証
        assertEquals(2, result.size());  // 受講生は2人
        assertEquals(2, result.get(0).getStudentCourseList().size());  // Aliceには2つのコース
        assertEquals(1, result.get(1).getStudentCourseList().size());  // Bobには1つのコース
    }

    @Test
    void 受講生がコースを持たない場合_空のコースリストが返されること() {
        Student student = new Student(1, "Alice");

        students = Collections.singletonList(student);
        courses = Collections.emptyList();

        List<StudentDetail> result = converter.convertStudentDetails(students, courses);

        assertEquals(1, result.size());
        assertTrue(result.get(0).getStudentCourseList().isEmpty());  // コースなし
    }

    @Test
    void 空のリストを渡した場合_空のリストが返されること() {
        students = Collections.emptyList();
        courses = Collections.emptyList();

        List<StudentDetail> result = converter.convertStudentDetails(students, courses);

        assertTrue(result.isEmpty());  // 結果も空のリスト
    }

    @Test
    void 受講生とコースのIDが一致しない場合_コースなしとして扱われること() {
        Student student = new Student(1, "Alice");
        StudentCourse course = new StudentCourse(1, 2, "Math");  // 違う受講生ID

        students = Collections.singletonList(student);
        courses = Collections.singletonList(course);

        List<StudentDetail> result = converter.convertStudentDetails(students, courses);

        assertEquals(1, result.size());
        assertTrue(result.get(0).getStudentCourseList().isEmpty());  // コースはマッチしない
    }
}
