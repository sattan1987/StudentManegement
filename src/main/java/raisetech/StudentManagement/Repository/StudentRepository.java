package raisetech.StudentManagement.Repository;

import org.apache.ibatis.annotations.*;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

import java.util.List;

/**
 * 受講生テーブルと受講生コース情報テーブルに紐づくrepositoryです。
 */

@Mapper
public interface StudentRepository {

    /**
     * 受講生の全件検索を行います。
     *
     * @return　受講生一覧（全件）
     */
    @Select("SELECT * FROM students")
    List<Student> search();

    /**
     * 受講生のコースの全件検索を行います
     *
     * @return　受講生のコース情報(全件）
     */
    @Select("SELECT * FROM students_courses")
    List<StudentCourse> searchStudentCoursesList();

    /**
     * 受講生を新規登録します。IDに関して自動採番を行う。
     *
     * @param student 　受講生
     */
    @Insert("INSERT INTO students (name, furigana, nick_name, email_address, address, age, gender, remark, isDeleted) " + "VALUES (#{name}, #{furigana}, #{nickName}, #{emailAddress}, #{address}, #{age}, #{gender}, #{remark}, false)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void registerStudent(Student student);

    /**
     * 受講生コース情報を新規登録します。IDに関して自動採番を行う。
     *
     * @param studentCourse 　受講生コース情報
     */
    @Insert("INSERT INTO students_courses (student_id, course, enrollment_start_date, enrollment_end_date) " + "VALUES (#{studentId}, #{course}, #{enrollmentStartDate}, #{enrollmentEndDate})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void registerStudentCourse(StudentCourse studentCourse);

    /**
     * 受講生を更新します。
     *
     * @param student 　受講生
     */
    @Update("UPDATE students SET name = #{name}, furigana = #{furigana}, nick_name = #{nickName}, email_address = #{emailAddress}, " + "address = #{address}, age = #{age}, gender = #{gender}, remark = #{remark}, isDeleted = #{isDeleted} WHERE id = #{id}")
    void updateStudent(Student student);

    /**
     * 受講生コース情報のコース名をを更新します。
     *
     * @param studentCourse 　受講生コース情報
     */
    @Update("UPDATE students_courses SET course = #{course} WHERE id = #{id} ")
    void updateStudentCourse(StudentCourse studentCourse);

    @Select("SELECT * FROM students WHERE id = #{id}")
    Student findById(int id);

    /**
     * 受講生IDに紐づく受講生コース情報を検索します。
     *
     * @return　受講生IDに紐づく受講生コース情報
     */
    @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
    List<StudentCourse> findCoursesByStudentId();

    @Select("SELECT * FROM students_courses WHERE id = #{id}")
    StudentCourse findCourseById(int id);

    @Update("UPDATE students SET isDeleted = true WHERE id = #{id}")
    void softDeleteStudent(int id);
}
