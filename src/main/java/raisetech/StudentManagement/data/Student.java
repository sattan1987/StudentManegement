package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生")
@Getter
@Setter
public class Student {


    @Min(1)
    @Max(value = 999,message = "999以下の数字のみ入力するようにしてください")
    private int id;
    @NotBlank
    private String name;
    @NotBlank
    private String furigana;
    @NotBlank
    private String nickName;
    @NotBlank
    private String emailAddress;
    @NotBlank
    private String address;
    @Min(10)
    @Max(99)
    private int age;
    private String gender;
    private String remark;
    private boolean deleted;

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







