package raisetech.StudentManagement;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentRepository {

    @Select("SELECT * FROM students")
    List<Student> search();


    @Select("SELECT * FROM Students_courses")
    List<Students_courses> searchCourses();}