package raisetech.StudentManagement.controller;

//import ch.qos.logback.core.model.Model;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.exception.TestException;
import raisetech.StudentManagement.service.StudentService;

import java.util.Arrays;
import java.util.List;

/**
 * 受講生のの検索や登録、更新などを行う　REST　APIとして実行されるcontrollerです。
 */
@Validated
@RestController
public class StudentController {


    private StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;

    }

    /**
     * 受講生詳細一覧検索です。全体検索のため、条件指定は行わないものになります。
     *
     * @return　受講生一覧（全件）
     */
    @Operation(summary = "一覧検索",description = "受講生の一覧を検索します。")
    @GetMapping("/studentList")
    public List<StudentDetail> getStudentList() {

        return service.searchStudentList();
    }

    /**
     * 受講生詳細の検索です。IDに紐づく任意の受講生の情報を取得します。
     *
     * @param id 　受講生ID
     * @return　受講生情報
     */
    @Operation(summary = "受講生番号検索",description = "受講生詳細の内容を受講生IDを使って検索します。")
    @GetMapping("/student/{id}")
    public StudentDetail getStudent(@PathVariable @Min(1) @Max(999) int id) {
        return service.getStudentDetailById(id);
    }

    @GetMapping("/newstudent")
    public String newStudent(Model model) {
        StudentDetail studentDetail = new StudentDetail();
        studentDetail.setStudentCourseList(Arrays.asList(new StudentCourse()));
        model.addAttribute("studentDetail", studentDetail);
        return "registerstudent";
    }

    /**
     * 受講生詳細のの登録を行います。
     *
     * @param studentDetail 　受講生詳細
     * @return　実行結果
     */
    @Operation(summary = "受講生登録",description = "受講生を登録します。")
    @PostMapping("/registerstudent")
    public ResponseEntity<StudentDetail> registerStudent(@RequestBody StudentDetail studentDetail) {

        StudentDetail responsestudentDetail = service.registerStudent(studentDetail);
        return ResponseEntity.ok(responsestudentDetail);

    }

    /**
     * 受講生詳細の更新を行います。
     * キャンセルフラグの更新もここで行います
     *
     * @param studentDetail 　受講生詳細
     * @return　実行結果
     */
    @Operation(summary = "受講生詳細更新",description = "受講生詳細の内容を更新します。")
    @PutMapping("/updateStudent")
    public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
        service.updateStudent(studentDetail);  // idと一緒に更新処理を行う
        return ResponseEntity.ok("更新に成功しました");

    }
    @Operation(summary = "エラーテスト",description = "エラーテストのため例外を発生させます。")
    @GetMapping("/test")
    public String testException() throws TestException {
        // 意図的に例外を発生させる
        throw new TestException("テスト例外が発生しました");
    }
}
