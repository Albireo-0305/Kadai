package com.example.S7.domain;

import com.example.S7.data.Student;
import com.example.S7.data.StudentsCourses;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class StudentDetail {

  private Student student;
  private List<StudentsCourses> studentsCourses;
}
