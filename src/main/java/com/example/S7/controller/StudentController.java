package com.example.S7.controller;

import com.example.S7.controller.converter.StudentConverter;
import com.example.S7.data.Student;
import com.example.S7.data.StudentsCourses;
import com.example.S7.domain.StudentDetail;
import com.example.S7.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 受講生の登録・検索・更新を行う REST API コントローラーです。
 */
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
   * @return 受講生のリスト
   */
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  /**
   * 新しい受講生を登録します。
   *
   * @param studentDetail 登録する受講生の詳細情報
   * @return 登録後の受講生詳細情報
   */
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(@RequestBody StudentDetail studentDetail) {
    // 学生情報とコース情報を登録
    StudentDetail responseStudentDetail = service.insertStudentWithCourse(studentDetail);
    return ResponseEntity.ok(responseStudentDetail);
  }

  /**
   * 指定されたIDの受講生情報を取得します（編集用）。
   *
   * @param studentId 学生ID
   * @return 学生詳細情報
   */
  @GetMapping("/editStudent/{id}")
  public ResponseEntity<StudentDetail> getStudentDetail(@PathVariable("id") int studentId) {
    Student student = service.findStudentById(studentId);
    StudentsCourses course = service.findCourseByStudentId(studentId);

    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);
    detail.setStudentsCourses(List.of(course));

    return ResponseEntity.ok(detail);
  }

  /**
   * 受講生情報を更新します。
   *
   * @param studentDetail 更新する学生の詳細情報
   * @return 成功メッセージ
   */
  @PostMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
    service.updateStudentWithCourse(studentDetail);
    return ResponseEntity.ok("更新が成功しました。");
  }
}

//リファクタリング箇所
// insertStudent()が重複で使っていなさそうなので削除しました。
