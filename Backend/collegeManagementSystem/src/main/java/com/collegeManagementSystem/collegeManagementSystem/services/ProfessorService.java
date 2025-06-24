package com.collegeManagementSystem.collegeManagementSystem.services;

import com.collegeManagementSystem.collegeManagementSystem.dto.ProfessorDTO;
import com.collegeManagementSystem.collegeManagementSystem.dto.StudentDTO;
import com.collegeManagementSystem.collegeManagementSystem.dto.SubjectDTO;
import com.collegeManagementSystem.collegeManagementSystem.entities.ProfessorEntity;

import java.util.List;
import java.util.Optional;

public interface ProfessorService {


    List<ProfessorDTO> getAllProfessors();

    Optional<ProfessorDTO> getProfessorById(Long id);

    ProfessorDTO createProfessor(ProfessorDTO professorDTO);

    ProfessorDTO updateProfessor(Long id, ProfessorDTO professorDTO);

    List<StudentDTO> getStudentsByProfessorId (Long professorId);

    List<SubjectDTO> getSubjectsByProfessorId(Long professorId);

    boolean deleteProfessor(Long id);
}
