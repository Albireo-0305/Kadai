package com.example.S7.controller.converter;

import com.example.S7.data.Student;
import com.example.S7.data.StudentCourse;
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
   * @param StudentList        学生の一覧
   * @param studentCourseList 各学生に紐づくコース情報の一覧
   * @return 受講生詳細（学生 + そのコース一覧）のリスト
   */
  public List<StudentDetail> convertStudentDetails(List<Student> StudentList,
      List<StudentCourse> studentCourseList) {
    List<StudentDetail> studentDetails = new ArrayList<>();

    for (Student student : StudentList) {
      // 学生に紐づくコースだけを抽出
      List<StudentCourse> convertStudentCourseList = studentCourseList.stream()
          .filter(course -> course.getStudentId() == student.getStudentId())
          .collect(Collectors.toList());

      // StudentDetail に設定
      StudentDetail detail = new StudentDetail(student, convertStudentCourseList);
      studentDetails.add(detail);
    }

    return studentDetails;
  }
}

//リファクタリング箇所
//for (Student student : students)に変更(for-each 文)
