package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Schema(description = "受講生のコース情報")
@Getter
@Setter
public class StudentCourse {
    private int id;
    @NotNull
    private int studentId;
    private String course;
    private LocalDateTime enrollmentStartDate;
    private LocalDateTime enrollmentEndDate;


    // デフォルトコンストラクタ
    public StudentCourse() {
    }

    // IDとコース名だけをセットするコンストラクタ
    public StudentCourse(int id, String course) {
        this.id = id;
        this.course = course;
    }

    // すべてのフィールドを適切にセットするコンストラクタ
    public StudentCourse(int id, int studentId, String course, LocalDateTime enrollmentStartDate, LocalDateTime enrollmentEndDate) {
        this.id = id;
        this.studentId = studentId;
        this.course = course;
        this.enrollmentStartDate = enrollmentStartDate;
        this.enrollmentEndDate = enrollmentEndDate;
    }
}