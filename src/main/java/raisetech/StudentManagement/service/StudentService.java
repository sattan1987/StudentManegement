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
import java.util.stream.Collectors;

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


