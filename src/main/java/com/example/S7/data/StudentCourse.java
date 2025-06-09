package com.example.S7.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース情報")
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
