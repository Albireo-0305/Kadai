package com.example.S7.repository;

import com.example.S7.data.Student;
import com.example.S7.data.StudentsCourses;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<Student> getAllstudents();

  @Select("SELECT * FROM Students_Courses")
  List<StudentsCourses> getAllStudentsCourses();

}
