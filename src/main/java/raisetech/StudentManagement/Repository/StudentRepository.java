package raisetech.StudentManagement.Repository;

import org.apache.ibatis.annotations.*;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;

import java.util.List;

@Mapper
public interface StudentRepository {

    @Select("SELECT * FROM students WHERE isDeleted = false")
    List<Student> search();

    @Select("SELECT * FROM students_courses")
    List<StudentsCourses> searchCourses();

    @Insert("INSERT INTO students (name, furigana, nick_name, email_address, address, age, gender, remark, isDeleted) " +
            "VALUES (#{name}, #{furigana}, #{nickName}, #{emailAddress}, #{address}, #{age}, #{gender}, #{remark}, false)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void saveStudent(Student student);

    @Insert("INSERT INTO students_courses (student_id, course, enrollment_start_date, enrollment_end_date) " +
            "VALUES (#{studentId}, #{course}, #{enrollmentStartDate}, #{enrollmentEndDate})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void saveStudentCourses(StudentsCourses studentsCourses);

    @Update("UPDATE students SET name = #{name}, furigana = #{furigana}, nick_name = #{nickName}, email_address = #{emailAddress}, " +
            "address = #{address}, age = #{age}, gender = #{gender}, remark = #{remark}, isDeleted = #{isDeleted} WHERE id = #{id}")
    void updateStudent(Student student);

    @Update("UPDATE students_courses SET course = #{course}, enrollment_start_date = #{enrollmentStartDate}, " +
            "enrollment_end_date = #{enrollmentEndDate} WHERE id = #{id} AND student_id = #{studentId}")
    void updateStudentCourses(StudentsCourses studentsCourses);

    @Select("SELECT * FROM students WHERE id = #{id}")
    Student findById(int id);

    @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
    List<StudentsCourses> findCoursesByStudentId(int studentId);

    @Select("SELECT * FROM students_courses WHERE id = #{id}")
    StudentsCourses findCourseById(int id);

    @Update("UPDATE students SET isDeleted = true WHERE id = #{id}")
    void softDeleteStudent(int id);
}
