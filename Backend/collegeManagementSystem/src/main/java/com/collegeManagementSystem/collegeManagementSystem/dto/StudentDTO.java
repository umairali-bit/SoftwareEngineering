package com.collegeManagementSystem.collegeManagementSystem.dto;

import lombok.Data;

import java.util.List;
@Data
public class StudentDTO {

    private Long id;
    private String name;
    private List<ProfessorDTO> professors;
    private List<SubjectDTO> subjects;

    public StudentDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
