package com.example.CollegeManagementSystem.CollegeManagementSystem.controllers;


import com.example.CollegeManagementSystem.CollegeManagementSystem.advices.ApiResponse;
import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.StudentDTO;
import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.SubjectDTO;
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

    //DELETE Student
    @DeleteMapping("/{studentID}")
    public ResponseEntity<Boolean> deleteStudent(@PathVariable Long studentID) {

        boolean deleted = studentService.deleteStudent(studentID);

        if(deleted) return ResponseEntity.ok(true);

        return ResponseEntity.notFound().build();
    }

    //PATACH Student - just for the admission record
    @PatchMapping("/{studentID}")
    public ResponseEntity<StudentDTO> patchStudent(@PathVariable Long studentID,
                                                   @RequestBody StudentDTO studentDTO) {
        StudentDTO patchStudent = studentService.patchStudent(studentID, studentDTO);

        return ResponseEntity.ok(patchStudent);


    }
    //POST mapping by adding simple guards in the controller
    @PostMapping("/assign-professor")
    public ResponseEntity<Void> assignProfessorToStudent(@RequestBody StudentDTO studentDTO) {
        if (studentDTO.getId() == null) {
            throw new IllegalArgumentException("studentId is required");
        }
        if (studentDTO.getProfessorIds() == null || studentDTO.getProfessorIds().isEmpty()) {
            throw new IllegalArgumentException("At least one professorId is required");
        }
        if (studentDTO.getSubjectIds() == null || studentDTO.getSubjectIds().isEmpty()) {
            throw new IllegalArgumentException("At least one subjectId is required");
        }

        Long studentId   = studentDTO.getId();
        Long professorId = studentDTO.getProfessorIds().iterator().next();
        Long subjectId   = studentDTO.getSubjectIds().iterator().next();

        studentService.assignProfessorToStudent(studentId, professorId, subjectId);
        return ResponseEntity.noContent().build(); // 204
    }







}
