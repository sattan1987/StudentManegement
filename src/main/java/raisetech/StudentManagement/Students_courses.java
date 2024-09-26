package raisetech.StudentManagement;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Students_courses {
    private String id;
    private String studentId;
    private String course;
    private Timestamp enrollmentStartDate;
    private Timestamp enrollmentEndDate;

}
