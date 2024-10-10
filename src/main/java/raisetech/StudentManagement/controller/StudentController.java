package raisetech.StudentManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StudentController {

    private StudentService service;
    private StudentConverter converter;

    @Autowired
    public StudentController(StudentService service, StudentConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @GetMapping("/studentList")
    public List<StudentDetail> getstudentlist() {
        List<Student> students = service.searchStudentList();
        List<StudentsCourses> studentsCourses = service.searchStudentCoursesList();

        return converter.convertStudentDetails(students, studentsCourses);
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
