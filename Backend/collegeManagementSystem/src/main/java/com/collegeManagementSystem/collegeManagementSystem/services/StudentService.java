package com.collegeManagementSystem.collegeManagementSystem.services;

import com.collegeManagementSystem.collegeManagementSystem.dto.StudentDTO;

import java.util.Optional;

public interface StudentService {

    Optional<StudentDTO> getStudentById(Long id);






}
