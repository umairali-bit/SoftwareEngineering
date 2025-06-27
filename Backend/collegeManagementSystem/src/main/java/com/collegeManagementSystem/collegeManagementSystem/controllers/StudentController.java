package com.collegeManagementSystem.collegeManagementSystem.controllers;


import com.collegeManagementSystem.collegeManagementSystem.dto.StudentDTO;
import com.collegeManagementSystem.collegeManagementSystem.dto.SubjectDTO;
import com.collegeManagementSystem.collegeManagementSystem.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id){
        return studentService.getStudentById(id)
                .map(student -> ResponseEntity.ok(student))
                .orElseThrow(() -> new NoSuchElementException("Student NOT found with ID: " + id));

    }

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent (@RequestBody StudentDTO studentDTO){
        StudentDTO savedStudent = studentService.createStudent(studentDTO);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO) {
        StudentDTO updatedStudent = studentService.updateStudent(id, studentDTO);
        return ResponseEntity.ok(updatedStudent);
    }

    @GetMapping("/{id}/subjects")
    public ResponseEntity<List<SubjectDTO>> getSubjectsByStudentId(@PathVariable Long id) {
        List<SubjectDTO> subjects = studentService.getSubjectsByStudentId(id);
        return ResponseEntity.ok(subjects);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteStudent(@PathVariable Long id) {
        boolean deleted = studentService.deleteStudent(id);
        if(deleted) return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }




}
