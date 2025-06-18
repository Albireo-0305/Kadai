package com.example.S7.controller.converter;

import static org.junit.jupiter.api.Assertions.*;

import com.example.S7.data.Student;
import com.example.S7.data.StudentCourse;
import com.example.S7.domain.StudentDetail;
import java.text.SimpleDateFormat;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class StudentConverterTest {

  private StudentConverter converter;

  @BeforeEach
  public void setUp() {
    converter = new StudentConverter();
  }

  @Test
  public void 変換結果が返ってきてコースリストが空であることをチェックする() {
    Student s1 = new Student();
    s1.setStudentId("1");
    s1.setName("武藤 遊戯");
    s1.setFurigana("むとう  ゆうぎ");
    s1.setNickName("遊戯王");
    s1.setEmailAddress("yu-gi-oh@example.com");
    s1.setRegion("童実野町");
    s1.setAge(17);
    s1.setGender("男性");
    s1.setRemark("");
    s1.setDeleted(false);

    Student s2 = new Student();
    s2.setStudentId("2");
    s2.setName("遊城 十代");
    s2.setFurigana("ゆうき　じゅうだい");
    s2.setNickName("ガッチャ");
    s2.setEmailAddress("yu-gi-ohgx@example.com");
    s2.setRegion("アカデミア　レッド寮");
    s2.setAge(16);
    s2.setGender("男性");
    s2.setRemark("");
    s2.setDeleted(false);

    Student s3 = new Student();
    s3.setStudentId("3");
    s3.setName("不動 遊星");
    s3.setFurigana("ふどう　ゆうせい");
    s3.setNickName("ダニエル");
    s3.setEmailAddress("yu-gi-oh5ds@example.com");
    s3.setRegion("ネオドミノシティ");
    s3.setAge(18);
    s3.setGender("男性");
    s3.setRemark(null);
    s3.setDeleted(false);

    List<Student> students = List.of(s1, s2, s3);
    List<StudentCourse> courses = List.of();

    List<StudentDetail> details = converter.convertStudentDetails(students, courses);

    //3人の学生詳細が返ること
    assertEquals(3, details.size());

    // それぞれコースが空リストであることを検証
    for (StudentDetail detail : details) {
      assertNotNull(detail.getStudent());
      assertNotNull(detail.getStudentCourseList());
      assertTrue(detail.getStudentCourseList().isEmpty());
    }
  }

  @Test
  public void コースありの紐づけが正しく行われているかチェックする() throws Exception {
    StudentConverter converter = new StudentConverter();

    Student student = new Student();
    student.setStudentId("4");
    student.setName("九十九遊馬");
    student.setFurigana("つくも　ゆうま");
    student.setNickName("先生");
    student.setEmailAddress("yu-gi-ohzexal@example.com");
    student.setRegion("ハートランド");
    student.setAge(13);
    student.setGender("男性");
    student.setRemark(null);
    student.setDeleted(false);

    List<Student> students = Collections.singletonList(student);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    StudentCourse course = new StudentCourse();
    course.setCourseId("4");
    course.setStudentId("4");
    course.setCourseName("特殊カード変質理論");
    course.setStartDate(sdf.parse("2011-04-10T15:00:00.000+00:00"));
    course.setExpectedEndDate(sdf.parse("2014-03-22T15:00:00.000+00:00"));

    List<StudentCourse> courses = Collections.singletonList(course);

    // 変換実行
    List<StudentDetail> result = converter.convertStudentDetails(students, courses);

    // 結果の検証
    assertEquals(1, result.size());

    StudentDetail detail = result.get(0);
    assertEquals("4", detail.getStudent().getStudentId());
    assertEquals("九十九遊馬", detail.getStudent().getName());

    // コースが1件紐づいているか
    List<StudentCourse> linkedCourses = detail.getStudentCourseList();
    assertEquals(1, linkedCourses.size());
    assertEquals("特殊カード変質理論", linkedCourses.get(0).getCourseName());
  }
}
