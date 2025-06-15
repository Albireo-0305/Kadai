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

  public String courseId;

  public String studentId;

  @NotBlank(message = "コース名を入力してください。")
  public String courseName;

  @NotNull(message = "開始日を入力してください。")
  public Date startDate;

  @NotNull(message = "終了予定日を入力してください。")
  public Date expectedEndDate;

}
