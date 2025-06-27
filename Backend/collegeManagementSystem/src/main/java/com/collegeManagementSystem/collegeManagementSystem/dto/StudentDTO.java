package com.collegeManagementSystem.collegeManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private Long id;
    private String name;

    @JsonIgnore
    private List<ProfessorDTO> professors = new ArrayList<>();

    private List<SubjectDTO> subjects = new ArrayList<>();

    private int subjectCount;

    public StudentDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
