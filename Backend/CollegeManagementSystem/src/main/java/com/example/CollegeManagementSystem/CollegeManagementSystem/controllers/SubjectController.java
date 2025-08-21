package com.example.CollegeManagementSystem.CollegeManagementSystem.controllers;


import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.SubjectDTO;
import com.example.CollegeManagementSystem.CollegeManagementSystem.services.SubjectServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.SpringVersion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
