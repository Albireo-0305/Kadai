package com.example.S7.controller;

import com.example.S7.controller.converter.StudentConverter;
import com.example.S7.data.Student;
import com.example.S7.data.StudentsCourses;
import com.example.S7.domain.StudentDetail;
import com.example.S7.service.StudentService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

  public StudentService service;
  public StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    List<Student> students = service.searchStudentList();
    List<StudentsCourses> studentsCourses = service.serchStudentsCoursesList();

    return converter.convertStudentDetails(students, studentsCourses);
  }


  @GetMapping("/StudentsCoursesList")
  public List<StudentsCourses> getStudentsCoursesList() {
    return service.serchStudentsCoursesList();
  }
}
