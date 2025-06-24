package com.collegeManagementSystem.collegeManagementSystem.services;

import com.collegeManagementSystem.collegeManagementSystem.dto.ProfessorDTO;
import com.collegeManagementSystem.collegeManagementSystem.dto.StudentDTO;
import com.collegeManagementSystem.collegeManagementSystem.dto.SubjectDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SubjectServiceImpl implements SubjectService{




    @Override
    public void subjectExistsById(Long id) {

    }

    @Override
    public List<SubjectDTO> getAllSubjects() {
        return null;
    }

    @Override
    public SubjectDTO getSubjectById(Long id) {
        return null;
    }

    @Override
    public SubjectDTO createSubject(SubjectDTO subjectDTO) {
        return null;
    }

    @Override
    public SubjectDTO updateSubject(Long id, SubjectDTO subjectDTO) {
        return null;
    }

    @Override
    public List<StudentDTO> getStudentBySubjectId(Long subjectId) {
        return null;
    }

    @Override
    public List<ProfessorDTO> getProfessorBySubjectId(Long subjectId) {
        return null;
    }

    @Override
    public boolean deleteSubject(Long id) {
        return false;
    }
}
