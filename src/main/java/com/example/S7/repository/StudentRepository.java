package com.example.S7.repository;

import com.example.S7.data.Student;
import com.example.S7.data.StudentCourse;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface StudentRepository {

  List<Student> search();

  List<StudentCourse> getAllStudentsCourses();

  Student findStudentById(int studentId);

  StudentCourse findCourseByStudentId(int studentId);

  List<StudentCourse> findCoursesByStudentId(int studentId);

  int insertStudent(Student student);

  void insertCourse(StudentCourse course);

  int updateStudent(Student student);

  void updateCourse(StudentCourse course);
}
