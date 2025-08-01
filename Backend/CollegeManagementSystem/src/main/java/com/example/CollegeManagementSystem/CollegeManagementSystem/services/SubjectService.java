package com.example.CollegeManagementSystem.CollegeManagementSystem.services;

import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.SubjectDTO;

import javax.security.auth.Subject;
import java.util.List;
import java.util.Map;

public interface SubjectService {

    // Basic CRUD
    SubjectDTO createSubject(SubjectDTO subjectDTO);

    SubjectDTO getSubjectById(Long subjectId);

    List<SubjectDTO> getAllSubjects();

    SubjectDTO updateSubject(Long id, SubjectDTO subjectDTO);

    boolean deleteSubjectById(Long id);

    // Partial update (PATCH semantics)
    SubjectDTO patchUpdateSubject(Long id, SubjectDTO subjectDTO);

    // Managing relationships
    void assignProfessorToSubject(Long subjectId, Long professorId);
//
//    void removeProfessorFromSubject(Long subjectId);
//
//    void assignStudentToSubject(Long subjectId, Long studentId);
//
//    void removeStudentFromSubject(Long subjectId, Long studentId);


}
