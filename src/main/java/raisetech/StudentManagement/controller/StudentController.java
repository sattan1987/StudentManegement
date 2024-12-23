package raisetech.StudentManagement.controller;

//import ch.qos.logback.core.model.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

import java.util.Arrays;
import java.util.List;

/**
 * 受講生のの検索や登録、更新などを行う　REST　APIとして実行されるcontrollerです。
 */

@RestController
public class StudentController {

    /**
     * 受講生サービス
     */
    private StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;

    }

    /**
     * 受講生一覧検索です
     * 全体検索のため、条件指定は行わないものになります。
     *
     * @return　受講生一覧（全件）
     */
    @GetMapping("/studentList")
    public List<StudentDetail> getStudentList() {
        return service.searchStudentList();
    }

    @GetMapping("/newStudent")
    public String newStudent(Model model) {
        StudentDetail studentDetail = new StudentDetail();
        studentDetail.setStudentsCourses(Arrays.asList(new StudentsCourses()));
        model.addAttribute("studentDetail", studentDetail);
        return "registerStudent";
    }

    @PostMapping("/registerStudent")
    public ResponseEntity<StudentDetail> registerStudent(@RequestBody StudentDetail studentDetail) {


        StudentDetail responsestudentDetail = service.registerStudent(studentDetail);
        return ResponseEntity.ok(responsestudentDetail);

    }

    //修正: 重複しているPOSTメソッドを削除し、id型を統一
    @PostMapping("/updateStudent")
    public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
        service.updateStudent(studentDetail);  // idと一緒に更新処理を行う
        return ResponseEntity.ok("更新に成功しました");

    }

    /**
     * 受講生検索です。
     * IDに紐づく任意の受講生の情報を取得します。
     *
     * @param id 　受講生ID
     * @return　受講生情報
     */
    @GetMapping("/Student/{id}")
    public StudentDetail updateStudent(@PathVariable int id) {
        return service.getStudentDetailById(id);
    }
}
