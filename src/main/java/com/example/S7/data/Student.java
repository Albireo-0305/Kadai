package com.example.S7.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生")
@Getter
@Setter
public class Student {


  private int studentId;

  @NotBlank(message = "名前を入力してください。")
  @Size(max = 100, min = 1)
  private String name;

  @NotBlank(message = "ふりがなを入力してください。")
  @Pattern(regexp = "^[ぁ-んー\\s]+$", message = "日本語で入力してください")
  private String furigana;

  @Size(min = 1, max = 100)
  private String nickName;

  @Email(message = "メールアドレスの形式が正しくありません。")
  @NotBlank(message = "メールアドレスを入力してください。")
  private String emailAddress;

  private String region;

  private int age;

  private String gender;

  private String remark;

  private boolean deleted;//LombokだとisDeleted に対して isIsDeleted() を自動生成してしまうので変更
}



