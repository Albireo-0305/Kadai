package com.example.S7.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

  private int studentId;

  @NotBlank
  @Size(max = 100,min = 1)
  private String name;

  @NotBlank
  private String furigana;

  @Size(min = 1,max = 100)
  private String nickName;

  @Email
  @NotBlank
  private String emailAddress;

  private String region;

  @Min(1)
  @Max(150)
  private int age;

  private String gender;

  private String remark;

  private boolean deleted;//LombokだとisDeleted に対して isIsDeleted() を自動生成してしまうので変更
}



