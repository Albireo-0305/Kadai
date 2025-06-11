package com.example.S7.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.S7.controller.converter.StudentConverter;
import com.example.S7.data.Student;
import com.example.S7.data.StudentCourse;
import com.example.S7.domain.StudentDetail;
import com.example.S7.exception.StudentNotFoundException;
import com.example.S7.repository.StudentRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;

  @BeforeEach
  void before() {
    sut = new StudentService(repository, converter);
  }

  @Test
  void 受講生詳細の一覧検索_リポジトリとコンバーターの処理を適切に呼び出せてること
      () {
    //事前準備
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();

    Mockito.when(repository.search()).thenReturn(studentList);
    Mockito.when(repository.getAllStudentsCourses()).thenReturn(studentCourseList);

    //実行
    List<StudentDetail> actual = sut.searchStudentList();

    //検証
    verify(repository, times(1)).search();
    verify(repository, times(1)).getAllStudentsCourses();
    verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList);

    //後処理   verify(repository,times(1)).getAllStudentsCourses();
    //ここでDBをもとに戻す
  }

  @Test
  void insertStudent_正しくリポジトリに登録されること() {
    Student student = new Student();

    sut.insertStudent(student);

    verify(repository, times(1)).insertStudent(student);
  }

  @Test
  void insertStudentWithCourse_学生とコースを登録できること() {
    Student student = new Student();
    student.setStudentId(1);
    StudentCourse course = new StudentCourse();
    List<StudentCourse> courseList = List.of(course);
    StudentDetail detail = new StudentDetail(student, courseList);

    StudentService spyService = Mockito.spy(sut);

    //spy・・・一部だけモックしたい時や部分的にテストしたい時に使う
    spyService.insertStudentWithCourse(detail);

    verify(repository).insertCourse(course);
  }

  @Test
  void findStudentDetailById_正しい詳細情報が返ってくる() {
    Student student = new Student();
    List<StudentCourse> courseList = List.of(new StudentCourse());

    Mockito.when(repository.findStudentById(1)).thenReturn(student);
    Mockito.when(repository.findCoursesByStudentId(1)).thenReturn(courseList);

    StudentDetail result = sut.findStudentDetailById(1);

    Assertions.assertEquals(student, result.getStudent());
    Assertions.assertEquals(courseList, result.getStudentCourseList());
  }

  @Test
  void findStudentById_存在する場合はそのまま返ってくる() {
    Student student = new Student();

    Mockito.when(repository.findStudentById(1)).thenReturn(student);

    Assertions.assertEquals(student, sut.findStudentById(1));
  }

  @Test
  void findStudentById_存在しない場合は例外がスローされる() {

    Mockito.when(repository.findStudentById(1)).thenReturn(null);

    Assertions.assertThrows(StudentNotFoundException.class, () -> sut.findStudentById(1));
  }

  @Test
  void updateStudentWithCourse_学生とコースの更新が行われる() {
    Student student = new Student();
    StudentCourse course = new StudentCourse();
    List<StudentCourse> courseList = List.of(course);
    StudentDetail detail = new StudentDetail(student, courseList);

    sut.updateStudentWithCourse(detail);

    verify(repository).updateStudent(student);
    verify(repository).updateCourse(course);
  }

  @Test
  void findCourseByStudentId_正しいコース情報が返ってくる() {
    StudentCourse course = new StudentCourse();

    Mockito.when(repository.findCourseByStudentId(1)).thenReturn(course);

    Assertions.assertEquals(course, sut.findCourseByStudentId(1));
  }
}
