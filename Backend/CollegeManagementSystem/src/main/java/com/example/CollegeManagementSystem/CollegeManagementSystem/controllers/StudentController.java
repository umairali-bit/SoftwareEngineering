package com.example.CollegeManagementSystem.CollegeManagementSystem.controllers;


import com.example.CollegeManagementSystem.CollegeManagementSystem.services.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentServiceImpl studentService;

    //create a new student

}
