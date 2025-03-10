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

    List<Student> search();

    /**
     * 受講生をIDで検索します
     *
     * @param id 　受講生ID
     * @return　受講生詳細
     */

    Student searchStudent(int id);

    /**
     * 受講生のコースの全件検索を行います
     *
     * @return　受講生のコース情報(全件）
     */

    List<StudentCourse> searchStudentCoursesList();

    /**
     * 受講生を新規登録します。IDに関して自動採番を行う。
     *
     * @param student 　受講生
     */

    void registerStudent(Student student);

    /**
     * 受講生コース情報を新規登録します。IDに関して自動採番を行う。
     *
     * @param studentCourse 　受講生コース情報
     */

    void registerStudentCourse(StudentCourse studentCourse);

    /**
     * 受講生を更新します。
     *
     * @param student 　受講生
     */
    void updateStudent(Student student);

    /**
     * 受講生コース情報のコース名をを更新します。
     *
     * @param studentCourse 　受講生コース情報
     */
    void updateStudentCourse(StudentCourse studentCourse);


    /**
     * 受講生IDに紐づく受講生コース情報を検索します。
     *
     * @return　受講生IDに紐づく受講生コース情報
     */
    List<StudentCourse> findCoursesByStudentId(int i);

}