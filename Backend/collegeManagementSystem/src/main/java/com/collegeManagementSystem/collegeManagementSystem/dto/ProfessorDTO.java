package com.collegeManagementSystem.collegeManagementSystem.dto;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import java.util.List;

@Data
public class ProfessorDTO {

    private Long id;
    private String title;
    private String name;
    @JsonManagedReference
    private List<SubjectDTO> subjects;
    private List<StudentDTO> students;

    public ProfessorDTO(Long id, String title, String name) {
        this.id = id;
        this.title = title;
        this.name = name;
    }

    public ProfessorDTO() {

    }
}
