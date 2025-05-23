package com.example.S7.controller;

import com.example.S7.controller.converter.StudentConverter;
import com.example.S7.data.Student;
import com.example.S7.data.StudentsCourses;
import com.example.S7.domain.StudentDetail;
import com.example.S7.service.StudentService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Date;

@Controller
public class StudentController {

  public StudentService service;
  public StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }


  @GetMapping("/studentList")
  public String getStudentList(Model model) {
    List<Student> students = service.searchStudentList();
    List<StudentsCourses> studentsCourses = service.serchStudentsCoursesList();

    model.addAttribute("studentList",
        converter.convertStudentDetails(students, studentsCourses));//こっちstudentListはテンプレートの名前
    return "studentList";//ファイル名
  }


  @GetMapping("/StudentsCoursesList")
  public List<StudentsCourses> getStudentsCoursesList() {
    return service.serchStudentsCoursesList();
  }

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
  }


  @GetMapping("newStudent")
  public String newStudent(Model model) {
    model.addAttribute("studentDetail", new StudentDetail());
    return "registerStudent";
  }

  @PostMapping("registerStudent")
  public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
    if (result.hasErrors()) {
      System.out.println("Validation errors found:");
      result.getAllErrors().forEach(e -> System.out.println(e.toString()));
      return "registerStudent";
    }
    //新規受講生情報を登録する処理の実装
    Student savedStudent = service.saveStudent(studentDetail.getStudent());
    //コース情報も一緒に登録できるようにする。新規で複数コースは考えにくいので単体でよい
    service.saveStudentWithCourse(studentDetail);

    return "redirect:/studentList";

  }

}

