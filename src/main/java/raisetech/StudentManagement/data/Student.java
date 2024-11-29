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

    // Getter
    public boolean getIsDeleted() { // "get" + プロパティ名 (isDeleted)
        return isDeleted;
    }

    // Setter
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Student orElseThrow(Object studentNotFound) {
        return null;
    }
}




