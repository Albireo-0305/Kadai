package com.example.S7;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<Student> Allstudents();

  @Select("SELECT * FROM Students_Courses")
  List<StudentsCourses> AllStudentsCourses();

}
