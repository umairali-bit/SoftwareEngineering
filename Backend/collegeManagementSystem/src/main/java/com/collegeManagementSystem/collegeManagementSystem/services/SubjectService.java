package com.collegeManagementSystem.collegeManagementSystem.services;

import com.collegeManagementSystem.collegeManagementSystem.dto.ProfessorDTO;
import com.collegeManagementSystem.collegeManagementSystem.dto.StudentDTO;
import com.collegeManagementSystem.collegeManagementSystem.dto.SubjectDTO;

import java.util.List;

public interface SubjectService {

    void subjectExistsById(Long id);
    List<SubjectDTO> getAllSubjects();
    SubjectDTO getSubjectById(Long id);

    SubjectDTO createSubject(SubjectDTO subjectDTO);
    SubjectDTO updateSubject(Long id, SubjectDTO subjectDTO);

    List<StudentDTO> getStudentBySubjectId(Long subjectId);
    List<ProfessorDTO> getProfessorBySubjectId(Long subjectId);

    boolean deleteSubject(Long id);
}
