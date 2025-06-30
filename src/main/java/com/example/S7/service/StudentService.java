package com.example.S7.service;

import com.example.S7.ApplicationStatus;
import com.example.S7.controller.converter.StudentConverter;
import com.example.S7.data.Student;
import com.example.S7.data.StudentCourse;
import com.example.S7.domain.StudentDetail;
import com.example.S7.exception.StudentNotFoundException;
import com.example.S7.repository.ApplicationStatusMapper;
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

  private final ApplicationStatusMapper statusMapper;
  private final StudentRepository repository;
  private final StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter,
      ApplicationStatusMapper statusMapper) {
    this.repository = repository;
    this.converter = converter;
    this.statusMapper = statusMapper;
  }

  /**
   * 受講生の全件一覧を取得します。
   */
  public List<StudentDetail> searchStudentList(String status,String name,String furigana,String emailAddress) {
    List<Student> students = repository.search();
    List<StudentCourse> courses = repository.getAllStudentsCourses();

    //受講生とコースをStudentDetailに変換する
    List<StudentDetail> allDetails = converter.convertStudentDetails(students,courses );

    // status が空や null の場合は全件返す
    if (status == null || status.isEmpty()) {
      return allDetails;
    }

    // status が指定されている場合は絞り込み
    return allDetails.stream()
        .filter(d->status==null||status.equals(d.getStatus()))
        .filter(d->name==null||(d.getStudent().getName()!=null && d.getStudent().getName().contains(name)))
        .filter(d->furigana==null||(d.getStudent().getFurigana()!=null && d.getStudent().getFurigana().contains(furigana)))
        .filter(d->emailAddress==null||(d.getStudent().getEmailAddress()!=null && d.getStudent().getEmailAddress().equals(emailAddress)))
        .toList();
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
   * 学生とコース、申し込み状況を同時に登録します。
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

      //申込状況の登録
      String statusValue = studentDetail.getStatus();
      if (statusValue != null && !statusValue.isEmpty()) {
        ApplicationStatus status = new ApplicationStatus();
        status.setStudentId(student.getStudentId());
        status.setCourseId(course.getCourseId());
        status.setStatus(studentDetail.getStatus());
        statusMapper.insert(status);
      }
    }

    return studentDetail;
  }

  /**
   * 指定IDの受講生の詳細を取得します。
   */
  public StudentDetail findStudentDetailById(int studentId) {
    Student student = repository.findStudentById(studentId);
    List<StudentCourse> courses = repository.findCoursesByStudentId(studentId);

    String statusValue = null;
    if (!courses.isEmpty()) {
      int courseId = courses.get(0).getCourseId();
      ApplicationStatus status = statusMapper.findStatus(studentId, courseId);
      if (status != null) {
        statusValue = status.getStatus();
      }
    }

    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);
    detail.setStudentCourseList(courses);
    detail.setStatus(statusValue);
    return detail;
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

      // ステータスの更新（null & 空文字チェックあり）
      ApplicationStatus status = statusMapper.findStatus(
          student.getStudentId(),
          course.getCourseId()
      );

      if (status != null && studentDetail.getStatus() != null && !studentDetail.getStatus()
          .isEmpty()) {
        status.setStatus(studentDetail.getStatus());
        statusMapper.update(status);
      }
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

  public StudentCourse findCourseByStudentId(int studentId) {
    return repository.findCourseByStudentId(studentId);
  }

}
