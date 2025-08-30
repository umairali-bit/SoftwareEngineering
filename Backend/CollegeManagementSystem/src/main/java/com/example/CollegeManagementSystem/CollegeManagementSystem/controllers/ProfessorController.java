package com.example.CollegeManagementSystem.CollegeManagementSystem.controllers;


import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.ProfessorDTO;
import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.StudentDTO;
import com.example.CollegeManagementSystem.CollegeManagementSystem.services.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/professors")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;

    //create a new professor
    @PostMapping
    public ResponseEntity<ProfessorDTO> createProfessor(@RequestBody ProfessorDTO professorDTO) {

        ProfessorDTO created = professorService.createProfessor(professorDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);

    }




}
