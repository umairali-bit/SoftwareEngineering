package com.example.CollegeManagementSystem.CollegeManagementSystem.controllers;


import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.StudentDTO;
import com.example.CollegeManagementSystem.CollegeManagementSystem.services.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentServiceImpl studentService;

    //create a new student
    @PostMapping
    public ResponseEntity<StudentDTO> createStudent (@RequestBody StudentDTO studentDTO) {
        StudentDTO created = studentService.createStudent(studentDTO);

        //201 created with the created student
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    //GET /api/students/{id}
    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        StudentDTO studentDTO = studentService.getStudentById(id);

        return ResponseEntity.ok(studentDTO);
    }

}
