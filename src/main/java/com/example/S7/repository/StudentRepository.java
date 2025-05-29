package com.example.S7.repository;

import com.example.S7.data.Student;
import com.example.S7.data.StudentsCourses;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * 受講生とコース情報に関するリポジトリです。
 */
@Mapper
public interface StudentRepository {

  //検索系

  /**
   * 論理削除されていない全ての受講生を取得します。
   */
  @Select("SELECT * FROM students WHERE is_deleted = FALSE")
  List<Student> getAllStudents();

  /**
   * 全受講生のコース情報を取得します。
   */
  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> getAllStudentsCourses();

  /**
   * 指定IDの受講生を1件取得します。
   */
  @Select("SELECT * FROM students WHERE student_id = #{studentId}")
  Student findStudentById(int studentId);

  /**
   * 指定IDのコースを1件取得します。
   */
  @Select("SELECT * FROM students_courses WHERE student_id = #{studentId} LIMIT 1")
  StudentsCourses findCourseByStudentId(int studentId);

  /**
   * 指定IDのコースをすべて取得します。
   */
  @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
  List<StudentsCourses> findCoursesByStudentId(int studentId);

  //登録系

  /**
   * 受講生を新規登録します。自動生成されたIDをセットします。
   */
  @Insert("""
          INSERT INTO students (name, furigana, nickname, email_address, region, age, gender, remark, is_deleted)
          VALUES (#{name}, #{furigana}, #{nickName}, #{emailAddress}, #{region}, #{age}, #{gender}, #{remark}, 0)
      """)
  @Options(useGeneratedKeys = true, keyProperty = "studentId")
  int insertStudent(Student student);

  /**
   * コース情報を登録します。
   */
  @Insert("""
          INSERT INTO students_courses (student_id, course_name, start_date, expected_end_date)
          VALUES (#{studentId}, #{courseName}, #{startDate}, #{expectedEndDate})
      """)
  void insertCourse(StudentsCourses course);

  //更新系

  /**
   * 受講生情報を更新します。
   */
  @Update("""
          UPDATE students SET
            name = #{name}, furigana = #{furigana}, nickname = #{nickName}, email_address = #{emailAddress},
            region = #{region}, age = #{age}, gender = #{gender}, remark = #{remark}, is_deleted = #{deleted}
          WHERE student_id = #{studentId}
      """)
  void updateStudent(Student student);

  /**
   * コース情報を更新します。
   */
  @Update("""
          UPDATE students_courses SET
            course_name = #{courseName}, expected_end_date = #{expectedEndDate}
          WHERE course_id = #{courseId}
      """)
  void updateCourse(StudentsCourses course);
}
