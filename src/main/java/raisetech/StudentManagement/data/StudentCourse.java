package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Schema(description = "受講生のコース情報")
@Getter
@Setter
public class StudentCourse {
    @NotNull(message = "コースIDは必須です")
    private Integer id;
    @NotNull
    private Integer studentId;
    private String course;
    private LocalDateTime enrollmentStartDate;
    private LocalDateTime enrollmentEndDate;
    @Schema(description = "申込ステータス（仮申込・本申込・受講中・受講終了）")
    @NotBlank(message = "ステータスは必須です")
    private String status;


    // デフォルトコンストラクタ
    public StudentCourse(int i, int i1, String java基礎, LocalDateTime localDateTime, LocalDateTime dateTime) {
    }// 引数で渡された値を使ってフィールドを設定するコンストラクター

    public StudentCourse(int id, int studentId, String course) {
        this.id = id;
        this.studentId = studentId;
        this.course = course;
    }


    // IDとコース名だけをセットするコンストラクタ
    public StudentCourse(int id, String course) {
        this.id = id;
        this.course = course;
    }

    // すべてのフィールドを適切にセットするコンストラクタ
    public StudentCourse(Integer id, int studentId, String course, LocalDateTime enrollmentStartDate, LocalDateTime enrollmentEndDate, String status) {
        this.id = id;
        this.studentId = studentId;
        this.course = course;
        this.enrollmentStartDate = enrollmentStartDate;
        this.enrollmentEndDate = enrollmentEndDate;
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        StudentCourse that = (StudentCourse) obj;
        return Objects.equals(id, that.id) &&
                Objects.equals(course, that.course) &&
                Objects.equals(studentId, that.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, course, studentId);
    }

}
