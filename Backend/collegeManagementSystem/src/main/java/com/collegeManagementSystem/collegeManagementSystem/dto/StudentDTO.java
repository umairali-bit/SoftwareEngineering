package com.collegeManagementSystem.collegeManagementSystem.dto;

import java.util.List;

public class StudentDTO {

    private Long id;
    private String name;
    private List<ProfessorDTO> professors;
    private List<SubjectDTO> subjects;

    public StudentDTO(Long id) {
        this.id = id;
    }
}
