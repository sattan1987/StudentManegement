package raisetech.StudentManagement;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@SpringBootApplication
@RestController
public class StudentManagementApplication {

    @Autowired
    private StudentRepository repository;


    public static void main(String[] args) {
        SpringApplication.run(StudentManagementApplication.class, args);
    }

    @GetMapping("/studentList")
    public List<Student> getstudentlist() {
        return repository.search();
    }

    @GetMapping("students_coursesList")
    public List<Students_courses> gerStudent_coursesList(){
        return repository.searchCourses();};
    }
