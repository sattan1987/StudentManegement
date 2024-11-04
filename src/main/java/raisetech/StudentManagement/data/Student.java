package raisetech.StudentManagement.data;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonTypeId;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.UUID;

@Getter
@Setter

public class Student {


    private String id = UUID.randomUUID().toString();
    private String name;
    private String furigana;
    private String nickName;
    private String emailAddress;
    private String address;
    private int age;
    private String gender;
    private String remark;
    private boolean isDeleted;
}


