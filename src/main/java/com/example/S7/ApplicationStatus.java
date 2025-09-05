package com.example.S7;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ApplicationStatus {

  private int id;
  private int studentId;
  private int courseId;
  private String status; //仮申込、本申込、受講中、受講終了
}
