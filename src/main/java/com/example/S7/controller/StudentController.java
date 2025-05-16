package com.example.S7.controller;

import com.example.S7.data.Student;
import com.example.S7.data.StudentsCourses;
import com.example.S7.service.StudentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

  public StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }


  @GetMapping("/studentList")
  public List<Student> getStudentList() {
    //リクエストの加工処理、入力チェックなど
    return service.searchStudentList();
  }

  @GetMapping("/StudentsCoursesList")
  public List<StudentsCourses> getStudentsCoursesList() {
    return service.serchStudentsCoursesList();
  }

}
