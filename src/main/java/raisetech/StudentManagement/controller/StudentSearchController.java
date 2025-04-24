package raisetech.StudentManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.service.StudentSearchService;

import java.util.List;

@RestController
public class StudentSearchController {

    @Autowired
    private StudentSearchService studentSearchService;

    // 名前で受講生を検索
    @Operation(
            summary = "受講生の名前検索",
            description = "名前を使って受講生の詳細情報を検索します。"
    )

    @GetMapping("/students/searchByName")
    public List<Student> searchStudentsByName(@RequestParam String name) {
        List<Student> students = studentSearchService.searchByName(name);
        if (students == null || students.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
        return students;
    }

    // ★ 追加: ResponseStatusException のメッセージを含める
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
    }

    // コースで受講生を検索（URLパスにコースを埋め込む）
    @Operation(
            summary = "受講生のコース検索",
            description = "コースを使って受講生の詳細情報を検索します。"
    )
    @GetMapping("/students/searchByCourse")
    public ResponseEntity<List<Student>> searchStudentsByCourse(@RequestParam String course) {
        List<Student> students = studentSearchService.searchByCourse(course);
        if (students.isEmpty()) {
            // 存在しないコースの場合、404エラーを返す
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
        return ResponseEntity.ok(students);
    }

    // 住所で受講生を検索（クエリパラメータで住所指定）
    @Operation(
            summary = "受講生の住所検索",
            description = "住所を使って受講生の詳細情報を検索します。"
    )
    @GetMapping("/students/searchByAddress")
    public ResponseEntity<List<Student>> searchStudentsByAddress(@RequestParam String address) {
        List<Student> students = studentSearchService.searchByAddress(address);
        if (students == null || students.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
        return ResponseEntity.ok(students);
    }

    @Operation(summary = "ふりがなで検索", description = "ふりがなで受講生を検索します。")
    @GetMapping("/students/searchByFurigana")
    public List<Student> searchByFurigana(@RequestParam String furigana) {
        List<Student> students = studentSearchService.searchByFurigana(furigana);
        if (students.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
        return students;
    }

    @Operation(summary = "ニックネームで検索", description = "ニックネームで受講生を検索します。")
    @GetMapping("/students/searchByNickName")
    public List<Student> searchByNickName(@RequestParam String nickName) {
        List<Student> students = studentSearchService.searchByNickName(nickName);
        if (students.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
        return students;
    }

    @Operation(summary = "年齢で検索", description = "年齢で受講生を検索します。")
    @GetMapping("/students/searchByAge")
    public List<Student> searchByAge(@RequestParam int age) {
        List<Student> students = studentSearchService.searchByAge(age);
        if (students.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
        return students;
    }

    @Operation(
            summary = "論理削除された受講生の検索",
            description = "削除済み（isDeleted = true）の受講生一覧を取得します。"
    )
    @GetMapping("/students/deleted")
    public ResponseEntity<List<Student>> searchDeletedStudents() {
        List<Student> deletedStudents = studentSearchService.searchDeletedStudents();
        if (deletedStudents.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No deleted students found");
        }
        return ResponseEntity.ok(deletedStudents);
    }

    @Operation(
            summary = "コース名で受講生を検索",
            description = "指定したコース名に該当する受講生を検索します。"
    )
    @GetMapping("/students/searchByCourseName")
    public ResponseEntity<List<Student>> searchByCourseName(@RequestParam String courseName) {
        List<Student> students = studentSearchService.searchByCourse(courseName);
        return ResponseEntity.ok(students); // 空でも200 OKを返す
    }


    @Operation(
            summary = "ステータスで受講生を検索",
            description = "契約ステータスで受講生を検索します。"
    )
    @GetMapping("/students/searchByStatus")
    public ResponseEntity<List<StudentCourse>> searchByStatus(@RequestParam String status) {
        List<StudentCourse> students = studentSearchService.searchByStatus(status);
        return ResponseEntity.ok(students); // 空でも200を返す
    }


}

