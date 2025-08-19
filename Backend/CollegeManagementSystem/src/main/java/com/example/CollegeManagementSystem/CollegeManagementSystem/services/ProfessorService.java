package com.example.CollegeManagementSystem.CollegeManagementSystem.services;

import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.ProfessorDTO;
import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.SubjectDTO;

import java.util.List;
import java.util.Set;

public interface ProfessorService {
    // Basic CRUD
    ProfessorDTO createProfessor(ProfessorDTO professorDTO);

    ProfessorDTO getProfessorById(Long id);

    List<ProfessorDTO> getAllProfessors();

    ProfessorDTO updateProfessor(Long id, ProfessorDTO professorDTO);

    void deleteProfessor(Long id);

    // PATCH (Partial update)
    ProfessorDTO patchProfessor(Long id, ProfessorDTO professorDTO);

    // Managing relationships
    void assignSubjectToProfessor(Long professorId, Set<Long> subjectIds);
//
//    void removeSubjectFromProfessor(Long professorId, Long subjectId);
//
//    void assignStudentToProfessor(Long professorId, Long studentId);
//
//    void removeStudentFromProfessor(Long professorId, Long studentId);
//
//    // New operation
//    Set<SubjectDTO> getSubjectsByProfessorId(Long professorId);
}
