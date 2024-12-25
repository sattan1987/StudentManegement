package raisetech.StudentManagement.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.Repository.StudentRepository;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 受講生情報を取り扱うサービスです。
 * 受講生の検索や登録・更新処理を行います
 */

@Service
public class StudentService {

    private StudentRepository repository;
    /**
     * 受講生コンバーター
     */
    private StudentConverter converter;


    @Autowired
    public StudentService(StudentRepository repository, StudentConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    /**
     * 受講生詳細の一覧検索です
     * 全体検索のため、条件指定は行わないものになります。
     *
     * @return　受講生一覧（全件）
     */
    public List<StudentDetail> searchStudentList() {
        List<Student> studentList = repository.search();
        List<StudentCourse> studentCourseList = repository.findCoursesByStudentId();
        return converter.convertStudentDetails(studentList, studentCourseList);
    }

    /**
     * 受講生詳細検索です。IDに紐づく受講生情報を取得した後、その受講生に紐づくコース情報を取得して設定します。
     *
     * @param id 　受講生ID
     * @return　受講生詳細
     */

    public StudentDetail getStudentDetailById(int id) {
        Student student = repository.searchStudent(id);
        List<StudentCourse> studentsCours = repository.findCoursesByStudentId();
        return new StudentDetail(student, studentsCours);
    }

    /**
     * 受講生詳細の登録を行います。
     * 受講生と受講生コース情報を個別に登録し、受講生コース情報に受講生情報を紐付ける値や日付情報（コース開始日,終了日)を設定をします。
     *
     * @param studentDetail 　受講生詳細
     * @return　登録情報を付与した受講生詳細
     */
    @Transactional
    public StudentDetail registerStudent(StudentDetail studentDetail) {
        Student student = studentDetail.getStudent();

        repository.registerStudent(student); // リポジトリを使って保存
        studentDetail.getStudentCourseList().forEach(studentsCourses -> {
            initStudentCourse(studentsCourses, student);
            repository.registerStudentCourse(studentsCourses);
        });
        return studentDetail;
    }

    /**
     * 受講生コース情報を登録する際の初期情報を設定する。
     *
     * @param studentCourse 　受講生コース情報
     * @param student       　受講生
     */
    private void initStudentCourse(StudentCourse studentCourse, Student student) {
        LocalDateTime now = LocalDateTime.now();

        studentCourse.setStudentId(student.getId());
        studentCourse.setEnrollmentStartDate(now);
        studentCourse.setEnrollmentEndDate(now.plusYears(1));
    }

    /**
     * 受講生情報の更新を行います。受講生と受講生情報をそれぞれ更新します。
     *
     * @param studentDetail　受講生詳細
     *
     */
    @Transactional
    public void updateStudent(StudentDetail studentDetail) {
        Student student = studentDetail.getStudent();
        repository.updateStudent(student);
        studentDetail.getStudentCourseList().forEach(updatedCourse -> repository.updateStudentCourse(updatedCourse));


    }


    @Transactional
    public void cancelStudentUpdate(int id) {
        // Student情報を取得し、isDeletedをtrueに設定
        Student student = repository.searchStudent(id);
        if (student == null) {
            throw new RuntimeException("Student not found"); // エラーハンドリング
        }
        student.setDeleted(true);  // 削除フラグを設定
        repository.updateStudent(student); // 更新処理を実行
    }
}