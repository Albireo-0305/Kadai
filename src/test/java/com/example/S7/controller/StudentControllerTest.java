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
    when(service.searchStudentList()).thenReturn(List.of(new StudentDetail()));

    mockMvc.perform(get("/studentList"))
        .andExpect(status().isOk())
        .andExpect(content().json("[{\"student\":null,\"studentCourseList\":null}]"));

    verify(service, times(1)).searchStudentList();
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

