package com.example.S7.data;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class StudentsCourses {

  public int courseId;
  public int studentId;
  public String courseName;
  public Date StartDate;
  public Date ExpectedendDate;

}
