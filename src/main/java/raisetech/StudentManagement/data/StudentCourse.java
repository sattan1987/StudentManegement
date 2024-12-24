package raisetech.StudentManagement.data;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StudentCourse {
    private int id;
    private int studentId;
    private String course;
    private LocalDateTime enrollmentStartDate;
    private LocalDateTime enrollmentEndDate;

}
