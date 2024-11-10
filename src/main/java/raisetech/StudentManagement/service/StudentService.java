package raisetech.StudentManagement.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.Repository.StudentRepository;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentService {

    private StudentRepository repository;

    @Autowired
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> searchStudentList() {
        return repository.search();
    }

    public List<StudentsCourses> searchStudentCoursesList() {
        return repository.searchCourses();

    }

    @Transactional
    public void saveStudent(StudentDetail studentDetail) {
        repository.saveStudent(studentDetail.getStudent()); // リポジトリを使って保存
        for (StudentsCourses studentsCourses : studentDetail.getStudentsCourses()) {
            studentsCourses.setStudentId(studentDetail.getStudent().getId());
            studentsCourses.setEnrollmentStartDate(LocalDateTime.now());
            studentsCourses.setEnrollmentEndDate(LocalDateTime.now().plusYears(1));
            repository.saveStudentCourses(studentsCourses);
        }
    }

    @Transactional
    public void updateStudent(int id, StudentDetail studentDetail) {
        // Student情報の更新
        Student student = studentDetail.getStudent();
        student.setId(id);
        repository.updateStudent(student);

        // StudentsCourses情報の更新
        for (StudentsCourses updatedCourse : studentDetail.getStudentsCourses()) {
            // 現在のデータを取得
            StudentsCourses existingCourse = repository.findCourseById(updatedCourse.getId());

            // enrollment_start_dateとenrollment_end_dateを既存の値で上書き
            updatedCourse.setEnrollmentStartDate(existingCourse.getEnrollmentStartDate());
            updatedCourse.setEnrollmentEndDate(existingCourse.getEnrollmentEndDate());

            updatedCourse.setStudentId(id);  // studentIdを設定
            repository.updateStudentCourses(updatedCourse);
        }


}


    public StudentDetail getStudentDetailById(int id) {
        Student student = repository.findById(id);
        List<StudentsCourses> studentsCourses = repository.findCoursesByStudentId(id);
        StudentDetail studentDetail = new StudentDetail();
        studentDetail.setStudent(student);
        studentDetail.setStudentsCourses(studentsCourses);
        return studentDetail;
    }



}

//    public List<Student> searchStudentsInTheir30s() {
//        return repository.search().stream()
//                .filter(student -> student.getAge() >= 30 && student.getAge() <= 39)
//                .collect(Collectors.toList());
//
//    }
//
//    public List<StudentsCourses>searchJavaCourseInfo() {
//        return repository.searchCourses().stream()
//                .filter(course -> course.getCourse().equals("Javaコース"))
//                .collect(Collectors.toList());
//    }


