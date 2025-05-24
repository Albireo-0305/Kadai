package com.example.S7.repository;

import com.example.S7.data.Student;
import com.example.S7.data.StudentsCourses;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<Student> getAllstudents();

  @Select("SELECT * FROM Students_Courses")
  List<StudentsCourses> getAllStudentsCourses();

  //登録用のSQL追加
  @Insert("""
        INSERT INTO students (name, furigana, nickname, email_address, region, age, gender, remark, is_deleted)
        VALUES (#{name}, #{furigana}, #{nickName}, #{emailAddress}, #{region}, #{age}, #{gender}, #{remark}, 0)
      """)//students内とVALUESは一致

  //キーの値を、自動でJavaのオブジェクトにセット
  @Options(useGeneratedKeys = true, keyProperty = "studentId")
  int insertStudent(Student student);

  @Insert("""
        INSERT INTO students_courses (student_id, course_name, start_date, expected_end_date)
        VALUES (#{studentId}, #{courseName}, #{startDate}, #{expectedEndDate})
      """)
  void insertCourse(StudentsCourses course);

  @Select("SELECT * FROM students WHERE student_id = #{studentId}")
  Student findStudentById(int studentId);

  // 1件だけ取得
  @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
  StudentsCourses findCourseByStudentId(int studentId);

  // 複数件を取得
  @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
  List<StudentsCourses> findCoursesByStudentId(int studentId);


  @Update("""
        UPDATE students SET
          name = #{name},furigana = #{furigana},nickname = #{nickName},email_address = #{emailAddress},
          region = #{region},age = #{age}, gender = #{gender}, remark = #{remark}
        WHERE student_id = #{studentId}
      """)
  void updateStudent(Student student);

  @Update("""
        UPDATE students_courses SET
          course_name = #{courseName},start_date = #{startDate}, expected_end_date = #{expectedEndDate}
        WHERE course_id = #{courseId}
      """)
  void updateCourse(StudentsCourses course);


}


