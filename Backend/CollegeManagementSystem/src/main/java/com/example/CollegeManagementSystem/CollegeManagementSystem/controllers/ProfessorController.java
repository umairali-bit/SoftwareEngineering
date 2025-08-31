package com.example.CollegeManagementSystem.CollegeManagementSystem.controllers;


import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.ProfessorDTO;
import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.StudentDTO;
import com.example.CollegeManagementSystem.CollegeManagementSystem.services.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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

    //UPDATE Professor
    @PutMapping("/{professorId}")
    public ResponseEntity<ProfessorDTO> updateStudent(@PathVariable Long professorId,
                                                      @RequestBody ProfessorDTO professorDTO) {

        ProfessorDTO professorDTO1 = professorService.updateProfessor(professorId, professorDTO);
        return ResponseEntity.ok(professorDTO1);


    }

    //DELETE mapping
    @DeleteMapping("/{professorId}")
    public ResponseEntity<ProfessorDTO> dtoResponseEntity(@PathVariable Long professorId) {

       professorService.deleteProfessor(professorId);

       return ResponseEntity.noContent().build();

    }

    //PATCH
    @PatchMapping("/{professorId}")
    public ResponseEntity<ProfessorDTO> patchProfessor (@PathVariable Long professorId,
                                                        @RequestBody ProfessorDTO professorDTO) {

        ProfessorDTO professorDTO1 = professorService.patchProfessor(professorId, professorDTO);
        return ResponseEntity.ok(professorDTO1);
    }

    //POST
    @PostMapping("/assign-subject")
    public ResponseEntity<ProfessorDTO> assignSubject (@RequestBody ProfessorDTO professorDTO) {

        if (professorDTO.getId() == null) {
            throw new RuntimeException("ProfessorID is required");
    }
        if (professorDTO.getSubjectIds() == null || professorDTO.getSubjectIds().isEmpty()) {
            throw new RuntimeException("Subject ID is required");
        }

        Long professorId = professorDTO.getId();

        // Pass the whole set directly
        professorService.assignSubjectToProfessor(professorId, professorDTO.getSubjectIds());
        return ResponseEntity.noContent().build();

    }

    //REMOVE /api/professors/{professorId}/subjects
    @DeleteMapping("/{professorId}/subjects")
    public ResponseEntity<Void> removeSubject(@PathVariable Long professorId,
                                              @RequestBody Set<Long> subjectId) {
        professorService.removeSubjectFromProfessor(professorId,subjectId);
        return ResponseEntity.noContent().build();

    }








}
