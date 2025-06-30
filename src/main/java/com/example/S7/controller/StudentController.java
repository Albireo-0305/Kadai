package com.example.S7.controller;

import com.example.S7.data.Student;
import com.example.S7.data.StudentCourse;
import com.example.S7.domain.StudentDetail;
import com.example.S7.service.StudentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 受講生の登録・検索・更新を行う REST API コントローラーです。
 */
@Validated
@RestController
public class StudentController {

  private final StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 日付の形式（yyyy-MM-dd）をバインド用に設定します。
   */
  @InitBinder
  public void initBinder(WebDataBinder binder) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
  }

  /**
   * 受講生の全件一覧を取得します。
   *
   * @param status 申込状況（仮申込・本申込・受講中・受講終了）での絞り込み
   * @return 受講生のリスト
   */
  @Operation(summary = "一覧検索", description = "受講生の一覧を検索します。")
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList(
      @RequestParam(required = false) String status,
      @RequestParam(required = false)String name,
      @RequestParam(required = false)String furigana,
      @RequestParam(required = false)String emailAddress
  ) {
    return service.searchStudentList(status,name,furigana,emailAddress);
  }

  /**
   * 新しい受講生を登録します。
   *
   * @param studentDetail 登録する受講生の詳細情報
   * @return 登録後の受講生詳細情報
   */
  @Operation(summary = "受講生登録", description = "受講生を登録します。")
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(@RequestBody StudentDetail studentDetail) {
    // 学生情報とコース情報を登録
    StudentDetail responseStudentDetail = service.insertStudentWithCourse(studentDetail);
    return ResponseEntity.ok(responseStudentDetail);
  }

  /**
   * 指定されたIDの受講生情報を取得します。
   *
   * @param studentId 学生ID
   * @return 学生詳細情報
   */
  @Operation(summary = "受講生情報取得", description = "指定されたIDの受講生とコースの情報を取得します。")
  @GetMapping("/editStudent/{id}")
  public ResponseEntity<StudentDetail> getStudentDetail(
      @PathVariable("id") @Min(1) @Max(999999999) @NotNull int studentId) {
    Student student = service.findStudentById(studentId);
    StudentCourse course = service.findCourseByStudentId(studentId);

    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);
    detail.setStudentCourseList(List.of(course));

    return ResponseEntity.ok(detail);
  }

  /**
   * 受講生情報を更新します。キャンセルフラグの更新もここで行います。(論理削除)
   *
   * @param studentDetail 更新する学生の詳細情報
   * @return 成功メッセージ
   */
  @Operation(summary = "受講生情報更新", description = "受講生とそのコース情報を更新します。")
  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail) {
    service.updateStudentWithCourse(studentDetail);
    return ResponseEntity.ok("更新が成功しました。");
  }
}

//リファクタリング箇所
// insertStudent()が重複で使っていなさそうなので削除しました。
