package com.collegeManagementSystem.collegeManagementSystem.controllers;


import com.collegeManagementSystem.collegeManagementSystem.dto.ProfessorDTO;
import com.collegeManagementSystem.collegeManagementSystem.dto.StudentDTO;
import com.collegeManagementSystem.collegeManagementSystem.dto.SubjectDTO;
import com.collegeManagementSystem.collegeManagementSystem.services.ProfessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(path = "/professor")
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping
    public ResponseEntity<List<ProfessorDTO>> getAllProfessors() {
        return ResponseEntity.ok(professorService.getAllProfessors());
    }

    @GetMapping(path ={"/{employeeId}"})
    public ResponseEntity<ProfessorDTO> getProfessorById(@PathVariable Long id) {
        return professorService.getProfessorById(id)
                .map(professor -> ResponseEntity.ok(professor))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping
    public ResponseEntity<ProfessorDTO> createProfessor(@RequestBody ProfessorDTO professorDTO) {
        ProfessorDTO created = professorService.createProfessor(professorDTO);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{professorId}")
    public ResponseEntity<ProfessorDTO> updateProfessor (@PathVariable Long id,
                                                         @RequestBody ProfessorDTO professorDTO) {
        ProfessorDTO updated = professorService.updateProfessor(id, professorDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{professorId}/students")
    public ResponseEntity<List<StudentDTO>> getStudentsByProfessorId(@PathVariable Long id) {
        return ResponseEntity.ok(professorService.getStudentsByProfessorId(id));
    }

    @GetMapping("/{professorId}/subjects")
    public ResponseEntity<List<SubjectDTO>> getSubjectsByProfessorId(@PathVariable Long id) {
        return ResponseEntity.ok(professorService.getSubjectsByProfessorId(id));
    }

    @DeleteMapping("/{professorId}")
    public ResponseEntity<Void> deleteProfessor(@PathVariable Long id) {
        boolean deleted = professorService.deleteProfessor(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }



}
