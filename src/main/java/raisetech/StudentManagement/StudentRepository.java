package raisetech.StudentManagement;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentRepository {

    @Select("SELECT * FROM student WHERE name = #{name}")
    Student searchByName(String name);

    @Insert("INSERT  INTO student(name,age,gender) VALUES(#{name} ,#{age},#{gender})")
    void registerStudent(String name, int age, String gender);

    @Update("UPDATE student SET age = #{age} , gender = #{gender} WHERE name =#{name}")
    void updateStudent(String name, int age, String gender);

    @Delete("DELETE FROM student WHERE name = #{name}")
    void deleteStudent(String name);

    @Select("SELECT * FROM student")
    List<Student> findAll();
}
