package com.example.CollegeManagementSystem.CollegeManagementSystem.controllers;


import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.SubjectDTO;
import com.example.CollegeManagementSystem.CollegeManagementSystem.services.SubjectServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.SpringVersion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectServiceImpl subjectService;

    //create a new subject
    @PostMapping
    public ResponseEntity<SubjectDTO> createSubject(@RequestBody SubjectDTO subjectDTO) {
        SubjectDTO created = subjectService.createSubject(subjectDTO);

        // 201 created with the created subject
        return ResponseEntity.status(HttpStatus.CREATED).body(created);

    }

    //GET /api/subjects/{id}
    @GetMapping("/{id}")
    public ResponseEntity<SubjectDTO> getSubjectById(@PathVariable Long id) {
        SubjectDTO subjectDTO = subjectService.getSubjectById(id);
        return ResponseEntity.ok(subjectDTO);
    }

    //GET all subjects
    @GetMapping
    public ResponseEntity<List<SubjectDTO>> getAllSubjects() {
        List<SubjectDTO> subjects = subjectService.getAllSubjects();
        return ResponseEntity.ok(subjects);

    }

    //Update Subject
    @PutMapping ("/{subjectId}")
    public ResponseEntity<SubjectDTO> updateSubject (
            @PathVariable Long subjectId,
            @RequestBody SubjectDTO subjectDTO) {

        SubjectDTO updatedSubject = subjectService.updateSubject(subjectId, subjectDTO);
        return ResponseEntity.ok(updatedSubject);



    }



}
