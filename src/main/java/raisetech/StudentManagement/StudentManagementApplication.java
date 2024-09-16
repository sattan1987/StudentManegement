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

    @GetMapping("/student")
    public String getStudent(@RequestParam String name) {
        Student student = repository.searchByName(name);
        return student.getName() + " " + student.getAge() + "歳,性別：" + student.getGender();
    }


    @PostMapping("/student")
    public void registerStudent(String name, int age, String gender) {
        repository.registerStudent(name, age, gender);
    }

    @PatchMapping("/student")
    public void updateStudent(String name, int age, String gender) {
        repository.updateStudent(name, age, gender);
    }

    @DeleteMapping("/student")
    public void deleteStudent(String name) {
        repository.deleteStudent(name);
    }

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return repository.findAll();
    }

}


