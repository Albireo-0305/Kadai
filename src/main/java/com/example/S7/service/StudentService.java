package com.example.S7.service;

import com.example.S7.controller.converter.StudentConverter;
import com.example.S7.data.Student;
import com.example.S7.data.StudentCourse;
import com.example.S7.domain.StudentDetail;
import com.example.S7.exception.StudentNotFoundException;
import com.example.S7.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 受講生の登録・検索・更新などを提供するサービスクラスです。
 */
@Service
public class StudentService {

  private final StudentRepository repository;
  private final StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 受講生の全件一覧を取得します。
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentCourse> studentCourseList = repository.getAllStudentsCourses();
    return converter.convertStudentDetails(studentList, studentCourseList);
  }

  /**
   * 新しい受講生を登録します。
   */
  @Transactional
  public Student insertStudent(Student student) {
    repository.insertStudent(student);
    return student;
  }

  /**
   * 学生とコースを同時に登録します。
   */
  @Transactional
  public StudentDetail insertStudentWithCourse(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    repository.insertStudent(student);

    List<StudentCourse> courses = studentDetail.getStudentCourseList();
    if (courses != null && !courses.isEmpty()) {
      StudentCourse course = courses.get(0);
      course.setStudentId(student.getStudentId());
      repository.insertCourse(course);
    }

    return studentDetail;
  }

  /**
   * 指定IDの受講生の詳細を取得します。
   */
  public StudentDetail findStudentDetailById(int studentId) {
    Student student = repository.findStudentById(studentId);
    List<StudentCourse> courses = repository.findCoursesByStudentId(studentId);
    return new StudentDetail(student, courses);
  }

  /**
   * 受講生情報とコースを更新します。
   */
  @Transactional
  public void updateStudentWithCourse(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    repository.updateStudent(student);

    List<StudentCourse> courses = studentDetail.getStudentCourseList();
    if (courses != null && !courses.isEmpty()) {
      StudentCourse course = courses.get(0);
      repository.updateCourse(course);
    }
  }

  /**
   * 学生情報のみを取得します（存在しない場合は例外をスロー）。
   */
  public Student findStudentById(int studentId) {
    Student student = repository.findStudentById(studentId);
    if (student == null) {
      throw new StudentNotFoundException("ID " + studentId + " の学生は存在しません。");
    }
    return student;
  }


  /**
   * 学生に紐づくコース情報を1件取得します。
   */
  public StudentCourse findCourseByStudentId(int studentId) {
    return repository.findCourseByStudentId(studentId);
  }

}

//リファクタリング箇所
//insertStudentWithCourseでinsertStudentを呼び出すようにしてControllerで両方呼んでいたのをサービス側で1つにまとめてみました。

//private finalを使いました。コンストラクタで注入されるフィールドは基本的に変更しないそうなので