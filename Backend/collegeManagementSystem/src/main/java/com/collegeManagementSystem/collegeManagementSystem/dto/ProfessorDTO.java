package com.collegeManagementSystem.collegeManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorDTO {
    private Long id;
    private String title;
    private String name;

    @JsonIgnore
    private List<SubjectDTO> subjects = new ArrayList<>();

    private List<StudentDTO> students = new ArrayList<>();


    public ProfessorDTO(Long id, String title, String name) {

    }
}
