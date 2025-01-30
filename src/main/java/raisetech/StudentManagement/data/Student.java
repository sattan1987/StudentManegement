package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生")
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
    private boolean deleted; // isDeleted → deleted に変更

    // デフォルトコンストラクタ
    public Student() {
    }

    // ID、名前、年齢を設定するコンストラクタ
    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    // すべてのフィールドを設定するコンストラクタ
    public Student(int id, String name, String furigana, String nickName, String emailAddress,
                   String address, int age, String gender, String remark, boolean deleted) {
        this.id = id;
        this.name = name;
        this.furigana = furigana;
        this.nickName = nickName;
        this.emailAddress = emailAddress;
        this.address = address;
        this.age = age;
        this.gender = gender;
        this.remark = remark;
        this.deleted = deleted;
    }
}







