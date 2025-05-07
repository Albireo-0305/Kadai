package com.example.S7;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentsCourses {

  public int courseid;
  public int studentid;
  public String coursename;
  public Date startdate;
  public Date expectedenddate;

}
