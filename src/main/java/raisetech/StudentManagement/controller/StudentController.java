package raisetech.StudentManagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.service.StudentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StudentController {

    private StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/studentList")
    public List<Student> getstudentlist() {
        return service.searchStudentList();
    }

    @GetMapping("studentsCoursesList")
    public List<StudentsCourses> getStudentCoursesList() {
        return service.searchStudentCoursesList();
    }

    @GetMapping("students30s")
    public List<Student> getStudentsIn30s() {
        return service.searchStudentsInTheir30s();
    }

    @GetMapping("/studentsJavaCourseInfo")
    public List<StudentsCourses> getJavaCourseInfo() {
        return service.searchJavaCourseInfo();
    }
}
