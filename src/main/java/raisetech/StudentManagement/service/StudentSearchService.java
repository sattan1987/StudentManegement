package raisetech.StudentManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.StudentManagement.Repository.StudentRepository;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

import java.util.List;

@Service
public class StudentSearchService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> searchByName(String name) {

        return studentRepository.searchByName(name);  // 修正されたメソッド名を使用
    }

    public List<Student> searchByCourse(String course) {
        return studentRepository.searchByCourse(course);  // 修正されたメソッド名を使用


    }

    public List<Student> searchByAddress(String address) {
        return studentRepository.searchByAddress(address);

    }

    public List<Student> searchByFurigana(String furigana) {
        return studentRepository.searchByFurigana(furigana);
    }

    public List<Student> searchByNickName(String nickName) {
        return studentRepository.searchByNickName(nickName);
    }

    public List<Student> searchByAge(int age) {
        return studentRepository.searchByAge(age);
    }

    public List<Student> searchDeletedStudents() {
        return studentRepository.searchDeletedStudents();
    }


    public List<StudentCourse> searchByStatus(String status) {
        return studentRepository.findByStatus(status); // 修正：findByStatusメソッドを使う
    }

    public List<Student> searchByCourseName(String courseName) {
        return studentRepository.searchByCourse(courseName); // 修正：コース名で検索するメソッドを呼び出す
    }
}
