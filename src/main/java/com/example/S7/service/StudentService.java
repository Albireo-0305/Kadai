package com.example.S7.service;

import com.example.S7.data.Student;
import com.example.S7.data.StudentsCourses;
import com.example.S7.domain.StudentDetail;
import com.example.S7.repository.StudentRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {

  public StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    return repository.getAllstudents();
  }

  public List<StudentsCourses> searchStudentsCoursesList() {
    return repository.getAllStudentsCourses();


  }

  //保存用のメソッドを追加
  @Transactional//必ず
  public Student insertStudent(Student student) {
    repository.insertStudent(student);
    return student;
  }

  @Transactional
  public void insertStudentWithCourse(StudentDetail studentDetail) {
    List<StudentsCourses> courses = studentDetail.getStudentsCourses();
    if (courses != null && !courses.isEmpty()) {
      StudentsCourses course = courses.get(0);
      course.setStudentId(studentDetail.getStudent().getStudentId()); // ここで紐づけをする
      repository.insertCourse(course);
    }
  }

  public StudentDetail findStudentDetailById(int studentId) {
    Student student = repository.findStudentById(studentId);
    List<StudentsCourses> courses = repository.findCoursesByStudentId(studentId);

    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);
    detail.setStudentsCourses(courses);

    return detail;
  }

  public void updateStudentWithCourse(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    repository.updateStudent(student);

    List<StudentsCourses> courses = studentDetail.getStudentsCourses();
    if (courses != null && !courses.isEmpty()) {
      StudentsCourses course = courses.get(0);
      repository.updateCourse(course);
    }
  }

  public Student findStudentById(int studentId) {

    return repository.findStudentById(studentId);
  }

  public StudentsCourses findCourseByStudentId(int studentId) {
    return repository.findCourseByStudentId(studentId);
  }
}
