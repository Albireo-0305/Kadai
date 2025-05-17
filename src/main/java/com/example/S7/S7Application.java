package com.example.S7;

import com.example.S7.data.Student;
import com.example.S7.data.StudentsCourses;
import com.example.S7.domain.StudentDetail;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class S7Application {


  public static void main(String[] args) {

    SpringApplication.run(S7Application.class, args);
  }


}
