package raisetech.StudentManagement.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.Repository.StudentRepository;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
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
     * 受講生一覧検索です
     * 全体検索のため、条件指定は行わないものになります。
     *
     * @return　受講生一覧（全件）
     */
    public List<StudentDetail> searchStudentList() {
        List<Student> studentList = repository.search();
        List<StudentsCourses> studentsCoursesList = repository.findCoursesByStudentId();
        return converter.convertStudentDetails(studentList, studentsCoursesList);
    }


    @Transactional
    public StudentDetail saveStudent(StudentDetail studentDetail) {
        repository.saveStudent(studentDetail.getStudent()); // リポジトリを使って保存
        for (StudentsCourses studentsCourses : studentDetail.getStudentsCourses()) {
            studentsCourses.setStudentId(studentDetail.getStudent().getId());
            studentsCourses.setEnrollmentStartDate(LocalDateTime.now());
            studentsCourses.setEnrollmentEndDate(LocalDateTime.now().plusYears(1));
            repository.saveStudentCourses(studentsCourses);
        }
        return studentDetail;
    }

    @Transactional
    public void updateStudent(StudentDetail studentDetail) {
        // Student情報の更新
        Student student = studentDetail.getStudent();
        repository.updateStudent(student);

        // StudentsCourses情報の更新
        for (StudentsCourses updatedCourse : studentDetail.getStudentsCourses()) {
            // 現在のデータを取得
            StudentsCourses existingCourse = repository.findCourseById(updatedCourse.getId());

            // enrollment_start_dateとenrollment_end_dateを既存の値で上書き
            updatedCourse.setEnrollmentStartDate(existingCourse.getEnrollmentStartDate());
            updatedCourse.setEnrollmentEndDate(existingCourse.getEnrollmentEndDate());

            updatedCourse.setStudentId(student.getId());  // studentIdを設定
            repository.updateStudentCourses(updatedCourse);

        }


    }

    /**
     * 受講生検索です。
     * IDに紐づく受講生情報を取得した後、その受講生に紐づくコース情報を取得して設定します。
     *
     * @param id 　受講生ID
     * @return　受講生情報
     */

    public StudentDetail getStudentDetailById(int id) {
        Student student = repository.findById(id);
        List<StudentsCourses> studentsCourses = repository.findCoursesByStudentId();
        return new StudentDetail(student,studentsCourses);
    }


    @Transactional
    public void cancelStudentUpdate(int id) {
        // Student情報を取得し、isDeletedをtrueに設定
        Student student = repository.findById(id);
        if (student == null) {
            throw new RuntimeException("Student not found"); // エラーハンドリング
        }
        student.setDeleted(true);  // 削除フラグを設定
        repository.updateStudent(student); // 更新処理を実行
    }
}