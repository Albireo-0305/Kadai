package com.example.S7.domain;

import com.example.S7.data.Student;
import com.example.S7.data.StudentsCourses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 受講生の詳細情報（学生＋受講コースの一覧）をまとめたクラスです。
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetail {

  /**
   * 学生情報です。
   */
  private Student student;

  /**
   * 学生が登録しているコースのリストです。
   */
  private List<StudentsCourses> studentsCourses;
}


