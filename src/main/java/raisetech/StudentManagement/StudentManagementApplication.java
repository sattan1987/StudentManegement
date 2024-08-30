package raisetech.StudentManagement;

import ch.qos.logback.core.util.StringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

      private String name = "Enami Kouji";
    private String age = "37";
    private Map<String, String> student = new HashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(StudentManagementApplication.class, args);
    }

    @GetMapping("/students")
    public Map<String, String> getStudent() {
        return student;
    }

    @PostMapping("/students")
    public void setStudent(String name, String age) {
        student.put("name", name);
        student.put("age", age);


    }

    @PostMapping("/studentName")
    public void updateStudentName(String name) {
        student.put("name", name);
    }


}

