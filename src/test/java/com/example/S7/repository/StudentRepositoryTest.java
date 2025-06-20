package com.example.S7.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.example.S7.data.Student;
import com.example.S7.data.StudentCourse;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索が行われること() {
    List<Student> actual = sut.search();
    assertThat(actual.size()).isEqualTo(7);
  }

  @Test
  void 受講生の登録が行えること() {
    Student student = new Student();
    student.setName("江並公史");
    student.setFurigana("えなみこうじ");
    student.setNickName("エナミ");
    student.setEmailAddress("test@example.com");

    sut.insertStudent(student);

    List<Student> actual = sut.search();

    assertThat(actual.size()).isEqualTo(8);

  }
}

