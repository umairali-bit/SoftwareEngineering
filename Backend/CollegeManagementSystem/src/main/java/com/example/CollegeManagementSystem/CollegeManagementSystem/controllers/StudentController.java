package com.example.CollegeManagementSystem.CollegeManagementSystem.controllers;


import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.StudentDTO;
import com.example.CollegeManagementSystem.CollegeManagementSystem.services.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.List;

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

    //GET all students
    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> studentDTOList = studentService.getAllStudents();

        return ResponseEntity.ok(studentDTOList);
    }

    //UPDATE Students
    @PutMapping ("/{studentId}")
    public ResponseEntity<StudentDTO> updateStudent (@PathVariable Long studentId,
                                                     @RequestBody StudentDTO studentDTO) {

        StudentDTO updateStudent = studentService.updateStudent(studentId, studentDTO);
        return ResponseEntity.ok(updateStudent);


    }

}
