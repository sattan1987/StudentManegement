package raisetech.StudentManagement;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import raisetech.StudentManagement.Repository.StudentRepository;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;

import java.util.List;


@SpringBootApplication

public class StudentManagementApplication {


    public static void main(String[] args) {

        SpringApplication.run(StudentManagementApplication.class, args);
    }
    
    @GetMapping("/studentList")
    public List<Student> getstudentlist() {
        return repository.search();
    }

    @GetMapping("students_coursesList")
    public List<StudentsCourses> gerStudent_coursesList(){
        return repository.searchCourses();};
    }

