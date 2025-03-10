package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
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
    private int id;
    @NotNull
    private int studentId;
    private String course;
    private LocalDateTime enrollmentStartDate;
    private LocalDateTime enrollmentEndDate;


    // デフォルトコンストラクタ
    public StudentCourse() {
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
    public StudentCourse(Integer id, int studentId, String course, LocalDateTime enrollmentStartDate, LocalDateTime enrollmentEndDate) {
        this.id = id;
        this.studentId = studentId;
        this.course = course;
        this.enrollmentStartDate = enrollmentStartDate;
        this.enrollmentEndDate = enrollmentEndDate;
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