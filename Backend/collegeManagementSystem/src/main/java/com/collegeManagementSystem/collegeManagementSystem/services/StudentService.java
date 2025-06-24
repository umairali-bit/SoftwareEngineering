package com.collegeManagementSystem.collegeManagementSystem.services;

import com.collegeManagementSystem.collegeManagementSystem.dto.ProfessorDTO;
import com.collegeManagementSystem.collegeManagementSystem.dto.StudentDTO;
import com.collegeManagementSystem.collegeManagementSystem.dto.SubjectDTO;

import java.util.List;
import java.util.Optional;

public interface StudentService {


    List<StudentDTO> getAllStudents();
    StudentDTO getStudentById(Long id);

    StudentDTO createStudent(StudentDTO studentDTO);
    StudentDTO updateStudent(Long id, StudentDTO studentDTO);

    List<SubjectDTO> getSubjectsByStudentId(Long studentId);


    boolean deleteStudent(Long id);






}
