package raisetech.StudentManagement.controller;

//import ch.qos.logback.core.model.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.service.StudentService;

import java.util.List;

@Controller
public class StudentController {

    private StudentService service;
    private StudentConverter converter;

    @Autowired
    public StudentController(StudentService service, StudentConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @GetMapping("/studentList")
    public String getStudentList(Model model) {
        List<Student> students = service.searchStudentList();
        List<StudentsCourses> studentsCourses = service.searchStudentCoursesList();

        model.addAttribute("studentList", converter.convertStudentDetails(students, studentsCourses));
        return "studentList";
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
