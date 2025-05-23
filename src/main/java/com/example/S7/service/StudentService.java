package com.example.S7.service;

import com.example.S7.data.Student;
import com.example.S7.data.StudentsCourses;
import com.example.S7.domain.StudentDetail;
import com.example.S7.repository.StudentRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  public List<StudentsCourses> serchStudentsCoursesList() {
    return repository.getAllStudentsCourses();


  }

  //保存用のメソッドを追加
  public Student saveStudent(Student student) {
    repository.insertStudent(student);
    return student;
  }

  public void saveStudentWithCourse(StudentDetail studentDetail) {
    List<StudentsCourses> courses = studentDetail.getStudentsCourses();
    if (courses != null && !courses.isEmpty()) {
      StudentsCourses course = courses.get(0);
      course.setStudentId(studentDetail.getStudent().getStudentId()); // ここで紐づけをする
      repository.insertCourse(course);
    }
  }

}
