package com.example.CollegeManagementSystem.CollegeManagementSystem.controllers;


import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.SubjectDTO;
import com.example.CollegeManagementSystem.CollegeManagementSystem.services.SubjectServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.SpringVersion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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

    //Patch Subject
    @PatchMapping("/{subjectID}")
    public ResponseEntity<SubjectDTO> patchSubject (@PathVariable Long subjectID, @RequestBody SubjectDTO subjectDTO) {
        SubjectDTO patchSubject = subjectService.patchUpdateSubject(subjectID, subjectDTO);
        return ResponseEntity.ok(patchSubject);

        /*
        @PatchMapping("/{subjectID}")
        public SubjectDTO patchSubject(@PathVariable Long subjectID, @RequestBody SubjectDTO subjectDTO) {
        return subjectService.patchUpdateSubject(subjectID, subjectDTO);
        }
         */
    }

    //DELETE Subject
    @DeleteMapping("/{subjectID}")
    public ResponseEntity<Boolean> deleteSubject(@PathVariable Long subjectID) {
        boolean deleted = subjectService.deleteSubjectById(subjectID);
        if(deleted) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    // Assign a professor to a subject
    @PutMapping("/{subjectId}/professors/{professorId}")
    public ResponseEntity<Void> assignProfessorToSubject(
            @PathVariable Long subjectId,
            @PathVariable Long professorId) {
        subjectService.assignProfessorToSubject(subjectId, professorId);

        // 204 No Content because we’re only updating the relation, not returning data
        return ResponseEntity.noContent().build();
    }

    // Remove professor from a Subject
    @DeleteMapping("/{subjectId}/professor")
    public ResponseEntity<Void> removeProfessorFromSubject(@PathVariable Long subjectId) {
        subjectService.removeProfessorFromSubject(subjectId);
        return  ResponseEntity.noContent().build();
    }

    // Assign students to a subject
    @PostMapping("/{subjectId}/students")
    public ResponseEntity<SubjectDTO> assignStudentsToSubject(
            @PathVariable Long subjectId,
            @RequestBody Set<Long> studentIds){

        subjectService.assignStudentToSubject(subjectId, studentIds);
        SubjectDTO updated = subjectService.getSubjectById(subjectId);
        return ResponseEntity.status(HttpStatus.CREATED).body(updated);


    }

    // Remove students from a subject
    @DeleteMapping("/{subjectId}/students")
    public ResponseEntity<Void> removeStudentsFromSubject(
            @PathVariable Long subjectId,
            @RequestBody Set<Long> studentIds) {

        subjectService.removeStudentFromSubject(subjectId, studentIds);
        return ResponseEntity.noContent().build(); // 204 No Content on success


    }








}
