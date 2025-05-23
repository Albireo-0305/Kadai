package com.example.S7.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

  private int studentId;
  private String name;
  private String furigana;
  private String nickName;
  private String emailAddress;
  private String region;
  private int age;
  private String gender;
  private String remark;
  private boolean deleted;//LombokだとisDeleted に対して isIsDeleted() を自動生成してしまうので変更
}



