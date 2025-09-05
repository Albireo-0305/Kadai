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

  @Test
  void 指定のIDの受講生を取得できること() {
    Student student = sut.findStudentById(1);
    assertThat(student.getName()).isEqualTo("武藤 遊戯");
  }

  @Test
  void 全てのコース情報が取得できること() {
    List<StudentCourse> courses = sut.getAllStudentsCourses();
    assertThat(courses).isNotEmpty();
  }

  @Test
  void 指定IDの受講生のコースが取得できること() {
    StudentCourse actual = sut.findCourseByStudentId(2);
    assertThat(actual).isNotNull();
    assertThat(actual.getCourseName()).isEqualTo("超融合理論");
  }

  @Test
  void コースを登録できること() {
    StudentCourse course = new StudentCourse();
    course.setStudentId(1);
    course.setCourseName("闇のゲーム理論");
    course.setStartDate(new Date());
    course.setExpectedEndDate(new Date());

    sut.insertCourse(course);

    List<StudentCourse> courses = sut.findCoursesByStudentId(1);
    assertThat(courses).extracting("courseName").contains("闇のゲーム理論");

  }

  @Test
  void 受講生情報を更新できること() {
    Student student = sut.findStudentById(1);
    student.setNickName("闇遊戯");

    sut.updateStudent(student);

    Student updated = sut.findStudentById(1);
    assertThat(updated.getNickName()).isEqualTo("闇遊戯");
  }

  @Test
  void コース情報の更新ができること() {
    List<StudentCourse> courses = sut.findCoursesByStudentId(2);
    StudentCourse course = courses.get(0);
    course.setCourseName("超越融合");

    sut.updateCourse(course);

    StudentCourse updated = sut.findCourseByStudentId(2);
    assertThat(updated.getCourseName()).isEqualTo("超越融合");
  }

  //異常系
  @Test
  void 存在してないIDで検索したときnullが返ってくること() {
    Student result = sut.findStudentById(9999);
    assertThat(result).isNull();
  }

  @Test
  void 必須項目のフリガナが抜けていたら登録できないこと() {
    Student student = new Student();
    student.setName("切札　勝舞");
    student.setNickName("しょうちゃん");
    student.setEmailAddress("duel@masters.com");

    assertThrows(Exception.class, () -> sut.insertStudent(student));
  }

  @Test
  void 存在しないIDを更新しようとした時に更新件数が0件になること() {
    Student student = new Student();
    student.setStudentId(9999);
    student.setName("切札　勝舞");
    student.setNickName("しょうちゃん");
    student.setEmailAddress("duel@masters.com");

    int updated = sut.updateStudent(student);
    assertThat(updated).isEqualTo(0);
  }
}

