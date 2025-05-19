package com.example.S7.repository;

import com.example.S7.data.Student;
import com.example.S7.data.StudentsCourses;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

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
      """)

  //キーの値を、自動でJavaのオブジェクトにセット
  @Options(useGeneratedKeys = true, keyProperty = "studentId")
  int insertStudent(Student student);

  @Insert("""
        INSERT INTO students_courses (student_id, course_name, start_date, expected_end_date)
        VALUES (#{studentId}, #{courseName}, #{startDate}, #{expectedEndDate})
      """)
  void insertCourse(StudentsCourses course);
}


