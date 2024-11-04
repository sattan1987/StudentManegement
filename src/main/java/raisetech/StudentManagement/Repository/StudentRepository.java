package raisetech.StudentManagement.Repository;

import org.apache.ibatis.annotations.*;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;

import java.util.List;


@Mapper
public interface StudentRepository {


    @Select("SELECT * FROM students")
    List<Student> search();


    @Select("SELECT * FROM students_courses")
    List<StudentsCourses> searchCourses();


    @Insert("INSERT INTO students (name, furigana,nick_name, email_address, address, age, gender, remark) " +
            "VALUES (#{name}, #{furigana}, #{nickName}, #{emailAddress}, #{address}, #{age}, #{gender}, #{remark})")
  @Options(useGeneratedKeys = true,keyProperty = "id")
    void save(Student student);
}