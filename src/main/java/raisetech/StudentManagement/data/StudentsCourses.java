package raisetech.StudentManagement.data;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StudentsCourses {
    private String id;
    private String studentId;
    private String course;
    private LocalDateTime enrollmentStartDate;
    private LocalDateTime enrollmentEndDate;

}
