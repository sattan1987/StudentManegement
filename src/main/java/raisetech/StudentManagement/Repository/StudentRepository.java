package raisetech.StudentManagement.Repository;

import org.apache.ibatis.annotations.*;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;

import java.util.List;



@Mapper
public interface StudentRepository {


    @Select("SELECT * FROM students")
    List<Student> search();


    @Select("SELECT * FROM Students_Courses")
    List<StudentsCourses> searchCourses();}