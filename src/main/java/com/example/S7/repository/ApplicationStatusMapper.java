package com.example.S7.repository;

import com.example.S7.ApplicationStatus;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ApplicationStatusMapper {

  ApplicationStatus findStatus(@Param("studentId") int studentId, @Param("courseId") int courseId);

  List<ApplicationStatus> findAll();

  void insert(ApplicationStatus status);

  void update(ApplicationStatus status);

}
