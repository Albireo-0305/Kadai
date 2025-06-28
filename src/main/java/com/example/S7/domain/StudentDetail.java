package com.example.S7.domain;

import com.example.S7.data.Student;
import com.example.S7.data.StudentCourse;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 受講生の詳細情報（学生＋受講コースの一覧）をまとめたクラスです。
 */
@Schema(description = "受講生詳細")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetail {

  /**
   * 学生情報です。
   */
  @Valid
  private Student student;

  /**
   * 学生が登録しているコースのリストです。
   */
  @Valid
  private List<StudentCourse> studentCourseList;

  /**
   * 申込状況を受け取る＆返すためのステータスです。
   */
  private String status; // 申込状況（仮申込・本申込・受講中・受講終了）

  public StudentDetail(Student student, List<StudentCourse> studentCourseList) {
    this.student = student;
    this.studentCourseList = studentCourseList;
  }
}


