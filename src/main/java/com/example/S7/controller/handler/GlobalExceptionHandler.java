package com.example.S7.controller.handler;

import com.example.S7.exception.StudentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * アプリ全体の共通例外処理クラス
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * 学生が存在しない場合の例外処理
   */
  @ExceptionHandler(StudentNotFoundException.class)
  public ResponseEntity<String> handleStudentNotFound(StudentNotFoundException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  /**
   * その他の例外処理（デフォルト）
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleAll(Exception ex) {
    ex.printStackTrace();
    return new ResponseEntity<>("エラーが発生しました", HttpStatus.INTERNAL_SERVER_ERROR);
  }
}

