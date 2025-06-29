package com.example.S7.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.S7.ApplicationStatus;
import com.example.S7.controller.converter.StudentConverter;
import com.example.S7.data.Student;
import com.example.S7.data.StudentCourse;
import com.example.S7.domain.StudentDetail;
import com.example.S7.exception.StudentNotFoundException;
import com.example.S7.repository.ApplicationStatusMapper;
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

  @Mock
  private ApplicationStatusMapper statusMapper;


  @BeforeEach
  void before() {
    sut = new StudentService(repository, converter, statusMapper);
  }

  @Test
  void 受講生詳細の一覧検索_リポジトリとコンバーターの処理を適切に呼び出せてること
      () {
    //事前準備
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();

    when(repository.search()).thenReturn(studentList);
    when(repository.getAllStudentsCourses()).thenReturn(studentCourseList);

    //実行
    List<StudentDetail> actual = sut.searchStudentList(null);

    //検証
    verify(repository, times(1)).search();
    verify(repository, times(1)).getAllStudentsCourses();
    verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList);

    //後処理   verify(repository,times(1)).getAllStudentsCourses();
    //ここでDBをもとに戻す
  }

  @Test
  void searchStudentList_ステータスがnullなら全件を返す() {
    StudentDetail d1 = new StudentDetail();
    StudentDetail d2 = new StudentDetail();

    Mockito.when(repository.search()).thenReturn(List.of());
    Mockito.when(repository.getAllStudentsCourses()).thenReturn(List.of());
    Mockito.when(converter.convertStudentDetails(Mockito.any(), Mockito.any()))
        .thenReturn(List.of(d1, d2));

    List<StudentDetail> result = sut.searchStudentList(null);

    Assertions.assertEquals(2, result.size());
  }

  //異常系テスト
  @Test
  void searchStudentList_存在しないステータスなら空が返る() {
    StudentDetail s1 = new StudentDetail();
    s1.setStatus("受講中");

    //空のリストを返す想定（検索結果なし）
    Mockito.when(repository.search()).thenReturn(List.of());
    Mockito.when(repository.getAllStudentsCourses()).thenReturn(List.of());

    // コンバーターも空リストを返す
    Mockito.when(converter.convertStudentDetails(Mockito.any(), Mockito.any()))
        .thenReturn(List.of(s1));

    List<StudentDetail> result = sut.searchStudentList("未登録");

    Assertions.assertTrue(result.isEmpty());
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

    when(repository.findStudentById(1)).thenReturn(student);
    when(repository.findCoursesByStudentId(1)).thenReturn(courseList);

    StudentDetail result = sut.findStudentDetailById(1);

    Assertions.assertEquals(student, result.getStudent());
    Assertions.assertEquals(courseList, result.getStudentCourseList());
  }

  @Test
  void findStudentById_存在する場合はそのまま返ってくる() {
    Student student = new Student();

    when(repository.findStudentById(1)).thenReturn(student);

    Assertions.assertEquals(student, sut.findStudentById(1));
  }

  @Test
  void findStudentById_存在しない場合は例外がスローされる() {

    when(repository.findStudentById(1)).thenReturn(null);

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

    when(repository.findCourseByStudentId(1)).thenReturn(course);

    Assertions.assertEquals(course, sut.findCourseByStudentId(1));
  }

  @Test
  void 申し込みの状況が登録されること() {
    Student student = new Student();
    student.setStudentId(777);

    StudentCourse course = new StudentCourse();
    course.setCourseId(888);
    List<StudentCourse> courseList = List.of(course);

    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);
    detail.setStudentCourseList(courseList);
    detail.setStatus("仮申込");

    //リポジトリをセット
    when(repository.insertStudent(student)).thenReturn(1); // ← int戻り値を設定
    doNothing().when(repository).insertCourse(course);

    sut.insertStudentWithCourse(detail);

    verify(statusMapper).insert(Mockito.argThat(status ->
        status.getStudentId() == 777 &&
            status.getCourseId() == 888 &&
            status.getStatus().equals("仮申込")
    ));
  }

  @Test
  void 申し込み状況が更新されること() {

    Student student = new Student();
    student.setStudentId(222);

    StudentCourse course = new StudentCourse();
    course.setCourseId(333);
    List<StudentCourse> courseList = List.of(course);

    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);
    detail.setStudentCourseList(courseList);
    detail.setStatus("受講中");

    ApplicationStatus existingStatus = new ApplicationStatus();
    existingStatus.setStudentId(222);
    existingStatus.setCourseId(333);
    existingStatus.setStatus("仮申込");

    //モックの戻り値
    when(statusMapper.findStatus(222, 333)).thenReturn(existingStatus);

    sut.updateStudentWithCourse(detail);

    verify(statusMapper).update(Mockito.argThat(status ->
        status.getStudentId() == 222 &&
            status.getCourseId() == 333 &&
            status.getStatus().equals("受講中")));
  }

  //異常系テスト
  @Test
  void statusがnull状態なら登録されないこと() {
    Student student = new Student();
    student.setStudentId(999);

    StudentCourse course = new StudentCourse();
    course.setCourseId(888);

    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);
    detail.setStudentCourseList(List.of(course));
    detail.setStatus(null);

    sut.insertStudentWithCourse(detail);

    verify(statusMapper, Mockito.never()).insert(Mockito.any());
  }

  @Test
  void courseListが空ならstatus登録されないこと() {
    Student student = new Student();
    student.setStudentId(999);

    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);
    detail.setStudentCourseList(new ArrayList<>());//空のリストをいれる
    detail.setStatus("仮申込");

    sut.insertStudentWithCourse(detail);

    verify(statusMapper, Mockito.never()).insert(Mockito.any());
  }

  @Test
  void ステータスが空なら更新しないこと() {
    Student student = new Student();
    student.setStudentId(111);

    StudentCourse course = new StudentCourse();
    course.setCourseId(222);

    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);
    detail.setStudentCourseList(List.of(course));
    detail.setStatus("受講終了");

    //findStatusがnullを返す＝ステータスが空
    Mockito.when(statusMapper.findStatus(111, 222)).thenReturn(null);

    sut.updateStudentWithCourse(detail);

    verify(statusMapper, Mockito.never()).update(Mockito.any());
  }
}
