package com.example.S7.service;

import com.example.S7.data.Student;
import com.example.S7.data.StudentsCourses;
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

    return repository.getAllstudents().stream()
        .filter(student -> student.getAge() <= 15)
        .toList();
  }

  public List<StudentsCourses> serchStudentsCoursesList() {
    return repository.getAllStudentsCourses().stream()
        .filter(course -> "LDSスタンダード".equals(course.getCourseName()))
        .toList();
  }
}
