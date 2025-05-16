package com.example.S7.domain;

import com.example.S7.data.Student;
import com.example.S7.data.StudentsCourses;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class StudentDetail {

  public Student student;
  public List<StudentsCourses> studentsCourses;


}
