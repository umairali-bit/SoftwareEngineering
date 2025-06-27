package com.collegeManagementSystem.collegeManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDTO {
    private Long id;
    private String title;

    @JsonIgnore
    private ProfessorDTO professor;

    @JsonIgnore
    private List<StudentDTO> students = new ArrayList<>();

    private int studentCount;

    public SubjectDTO(Long id, String title) {

    }
}
