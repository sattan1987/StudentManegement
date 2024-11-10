package raisetech.StudentManagement.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Student {

    private int id;
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


