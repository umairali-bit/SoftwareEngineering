package com.example.CollegeManagementSystem.CollegeManagementSystem.controllers;


import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.ProfessorDTO;
import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.StudentDTO;
import com.example.CollegeManagementSystem.CollegeManagementSystem.services.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //GET /api/professors/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ProfessorDTO> getProfessorBytId(@PathVariable Long id) {

        ProfessorDTO professorDTO = professorService.getProfessorById(id);
        return ResponseEntity.ok(professorDTO);
    }

    //GET all students
    @GetMapping
    public ResponseEntity<List<ProfessorDTO>> getAllProfessors() {

        List<ProfessorDTO>  professorDTOList = professorService.getAllProfessors();

        return ResponseEntity.ok(professorDTOList);
    }






}
