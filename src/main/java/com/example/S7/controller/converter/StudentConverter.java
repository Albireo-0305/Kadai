package com.example.S7.controller.converter;

import com.example.S7.data.Student;
import com.example.S7.data.StudentsCourses;
import com.example.S7.domain.StudentDetail;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 受講生詳細（StudentDetail）への変換を行うコンバーターです。
 */
@Component
public class StudentConverter {

  /**
   * 学生とコース情報から受講生詳細リストを作成します。
   *
   * @param students        学生の一覧
   * @param studentsCourses 各学生に紐づくコース情報の一覧
   * @return 受講生詳細（学生 + そのコース一覧）のリスト
   */
  public List<StudentDetail> convertStudentDetails(List<Student> students,
      List<StudentsCourses> studentsCourses) {
    List<StudentDetail> studentDetails = new ArrayList<>();

    for (Student student : students) {
      // 学生に紐づくコースだけを抽出
      List<StudentsCourses> convertStudentCourses = studentsCourses.stream()
          .filter(course -> course.getStudentId() == student.getStudentId())
          .collect(Collectors.toList());

      // StudentDetail に設定
      StudentDetail detail = new StudentDetail(student, convertStudentCourses);
      studentDetails.add(detail);
    }

    return studentDetails;
  }
}

//リファクタリング箇所
//for (Student student : students)に変更(for-each 文)
