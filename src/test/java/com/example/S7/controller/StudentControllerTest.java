package com.example.S7.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.example.S7.data.Student;
import com.example.S7.data.StudentCourse;
import com.example.S7.domain.StudentDetail;
import com.example.S7.service.StudentService;
import com.jayway.jsonpath.JsonPath;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private StudentService service;

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  @DisplayName("受講生詳細の一覧検索テスト 　　ステータス指定なし")
  void 受講生詳細の一覧検索が実行できて空のリストが返ってくること() throws Exception {
    when(service.searchStudentList(null,null,null,null)).thenReturn(List.of(new StudentDetail()));

    mockMvc.perform(get("/studentList"))
        .andExpect(status().isOk())
        .andExpect(content().json("[{\"student\":null,\"studentCourseList\":null}]"));

    verify(service, times(1)).searchStudentList(null,null,null,null);
  }

  @Test
  void 受講生詳細の一覧検索_ステータス仮申込で絞り込み() throws Exception {
    StudentDetail detail = new StudentDetail();
    detail.setStatus("仮申込");

    when(service.searchStudentList("仮申込",null,null,null)).thenReturn(List.of(detail));

    mockMvc.perform(get("/studentList").param("status", "仮申込"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].status").value("仮申込"));

    verify(service, times(1)).searchStudentList("仮申込",null,null,null);
  }

  @Test
  void 名前で検索できること()throws Exception{
    Student student = new Student();
    student.setName("不動 遊星");
    StudentDetail detail =new StudentDetail();
    detail.setStudent(student);

    when(service.searchStudentList(null,"不動",null,null)).thenReturn(List.of(detail));

    mockMvc.perform(get("/studentList").param("name","不動"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].student.name").value("不動 遊星"));

    verify(service).searchStudentList(null,"不動",null,null);
  }

  @Test
  void フリガナとメールアドレスで検索できること() throws Exception {
    Student student = new Student();
    student.setFurigana("ふどう ゆうせい");
    student.setEmailAddress("yusei@example.com");

    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);

    when(service.searchStudentList(null, null, "ふどう ゆうせい", "yusei@example.com"))
        .thenReturn(List.of(detail));

    mockMvc.perform(get("/studentList")
            .param("furigana", "ふどう ゆうせい")
            .param("emailAddress", "yusei@example.com"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].student.furigana").value("ふどう ゆうせい"))
        .andExpect(jsonPath("$[0].student.emailAddress").value("yusei@example.com"));

    verify(service).searchStudentList(null, null, "ふどう ゆうせい", "yusei@example.com");
  }

  //異常系テスト
  @Test
  void 存在しないステータスで検索すると空リストが返る() throws Exception {
    when(service.searchStudentList("存在しない",null,null,null)).thenReturn(List.of());

    mockMvc.perform(get("/studentList").param("status", "存在しない"))
        .andExpect(status().isOk())
        .andExpect(content().json("[]"));

    verify(service, times(1)).searchStudentList("存在しない",null,null,null);
  }

  @Test
  void 存在しない名前なら空リストが返る() throws Exception {
    when(service.searchStudentList(null, "城之内くん", null, null)).thenReturn(List.of());

    mockMvc.perform(get("/studentList").param("name", "城之内くん"))
        .andExpect(status().isOk())
        .andExpect(content().json("[]"));

    verify(service).searchStudentList(null, "城之内くん", null, null);
  }


  @Test
  void 受講生詳細の受講生で適切な値を入力した時に入力チェックに異常が発生しないこと() {
    Student student = new Student();
    student.setStudentId(1);
    student.setName("江並公史");
    student.setFurigana("えなみこうじ");
    student.setNickName("エナミ");
    student.setEmailAddress("test@example.com");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(0);

  }

  //registerStudent のPOST（登録）
  @Test
  void 受講生登録APIが200を返しデータを返すこと() throws Exception {
    StudentDetail detail = new StudentDetail();
    Student student = new Student();
    student.setStudentId(7);
    student.setName("王道 遊我");
    detail.setStudent(student);

    when(service.insertStudentWithCourse(any())).thenReturn(detail);

    mockMvc.perform(post("/registerStudent")
            .contentType("application/json")
            .content("""
                  {
                    "student": {
                      "studentId": 7,
                      "name": "王道 遊我",
                      "furigana": "おうどう ゆうが",
                      "nickName": "ゴーハの革命児",
                      "emailAddress": "yuga@goharules.com",
                      "region": "ゴーハ市",
                      "age": 11,
                      "gender": "男性",
                      "remark": "",
                      "deleted": false
                    },
                    "studentCourseList": [
                      {
                        "courseId": 7,
                        "studentId": 7,
                        "courseName": "ラッシュ学部",
                        "startDate": "2025-04-01T00:00:00.000+09:00",
                        "expectedEndDate": "2026-03-31T00:00:00.000+09:00"
                      }
                    ]
                  }
                """))
        .andExpect(status().isOk());
  }


  //editStudent/{id} のGET（詳細取得）
  @Test
  void 指定IDでの受講生取得APIが200を返すこと() throws Exception {
    Student student = new Student();
    student.setStudentId(3);
    student.setName("不動 遊星");

    StudentCourse course = new StudentCourse();
    course.setCourseId(999);

    when(service.findStudentById(3)).thenReturn(student);
    when(service.findCourseByStudentId(3)).thenReturn(course);

    mockMvc.perform(get("/editStudent/3"))
        .andExpect(status().isOk());
  }


  //updateStudent のPUT（更新）
  @Test
  void 受講生更新APIが200を返すこと() throws Exception {
    mockMvc.perform(put("/updateStudent")
            .contentType("application/json")
            .content("""
                {
                  "student": {
                    "studentId": 4,
                    "name": "九十九 遊馬",
                    "furigana": "つくも ゆうま",
                    "nickName": "先生",
                    "emailAddress": "zexal@example.com",
                    "region": "ハートランド",
                    "age": 13,
                    "gender": "男性",
                    "remark": "",
                    "deleted": false
                  },
                  "studentCourseList": [
                  {
                   "courseId": 10,
                   "studentId": 4,
                   "courseName": "ラッシュ学部",
                   "startDate": "2025-04-01",
                   "expectedEndDate": "2026-03-31"
                    }
                                            ]
                }
                """))
        .andExpect(status().isOk())
        .andExpect(content().string("更新が成功しました。"));
  }

}

