package raisetech.StudentManagement.controller;

//import ch.qos.logback.core.model.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

import java.util.Arrays;
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

    @GetMapping("/newStudent")
    public String newStudent(Model model) {
        StudentDetail studentDetail = new StudentDetail();
        studentDetail.setStudentsCourses(Arrays.asList(new StudentsCourses()));
        model.addAttribute("studentDetail", studentDetail);
        return "registerStudent";
    }

    @PostMapping("/registerStudent")
    public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
        if (result.hasErrors()) {
            return "registerStudent";
        }
        service.saveStudent(studentDetail);
        return "redirect:/studentList";
    }

    @GetMapping("/updateStudent/{id}")
    public String updateStudent(@PathVariable("id") int id, Model model) {
        StudentDetail studentDetail = service.getStudentDetailById(id);
        model.addAttribute("studentDetail", studentDetail);
        return "updateStudent";
    }

    // 修正: 重複しているPOSTメソッドを削除し、id型を統一
    @PostMapping("/updateStudent/{id}")
    public String updateStudent(@PathVariable("id") int id, @ModelAttribute StudentDetail studentDetail, BindingResult result) {
        if (result.hasErrors()) {
            return "updateStudent";
        }
        service.updateStudent(id, studentDetail);  // idと一緒に更新処理を行う
        return "redirect:/studentList";
    }

}

//    @GetMapping("studentsCoursesList")
//    public List<StudentsCourses> getStudentCoursesList() {
//        return service.searchStudentCoursesList();
//    }
//
//    @GetMapping("students30s")
//    public List<Student> getStudentsIn30s() {
//        return service.searchStudentsInTheir30s();
//    }
//
//    @GetMapping("/studentsJavaCourseInfo")
//    public List<StudentsCourses> getJavaCourseInfo() {
//        return service.searchJavaCourseInfo();
//    }

