package com.example.S7.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class StudentCourse {

  public int courseId;

  public int studentId;

  @NotBlank
  public String courseName;

  @NotNull
  public Date startDate;

  @NotNull
  public Date expectedEndDate;

}
