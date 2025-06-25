package com.collegeManagementSystem.collegeManagementSystem.controllers;


import com.collegeManagementSystem.collegeManagementSystem.dto.ProfessorDTO;
import com.collegeManagementSystem.collegeManagementSystem.dto.StudentDTO;
import com.collegeManagementSystem.collegeManagementSystem.dto.SubjectDTO;
import com.collegeManagementSystem.collegeManagementSystem.repositories.SubjectRepository;
import com.collegeManagementSystem.collegeManagementSystem.services.SubjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("subjects")
public class SubjectController {

    private final SubjectService subjectService;


    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;

    }

    @GetMapping
    public ResponseEntity<List<SubjectDTO>> getAllSubjects() {
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectDTO> getSubjectById(@PathVariable Long id) {
        return subjectService.getSubjectById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NoSuchElementException("Subject NOT found with ID: " + id));
    }

    // Get all students enrolled in a subject by subject ID
    @GetMapping("/{id}/students")
    public ResponseEntity<List<StudentDTO>> getStudentsBySubjectId(@PathVariable Long id) {
        List<StudentDTO> students = subjectService.getStudentBySubjectId(id);
        return ResponseEntity.ok(students);
    }

    // Get the professor teaching a subject by subject ID
    @GetMapping("/{id}/professor")
    public ResponseEntity<List<ProfessorDTO>> getProfessorBySubjectId(@PathVariable Long id) {
        List<ProfessorDTO> professors = subjectService.getProfessorBySubjectId(id);
        return ResponseEntity.ok(professors);
    }



    @PostMapping
    public ResponseEntity<SubjectDTO> createSubject(@RequestBody SubjectDTO subjectDTO) {
        SubjectDTO savedSubject = subjectService.createSubject(subjectDTO);
        return new ResponseEntity<>(savedSubject, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectDTO> updateSubject (@RequestBody SubjectDTO subjectDTO,
                                                     @PathVariable Long id) {
        return ResponseEntity.ok(subjectService.updateSubject(id,subjectDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteSubject(@PathVariable Long id) {
        boolean deleted = subjectService.deleteSubject(id);
        if(deleted) return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }





}


