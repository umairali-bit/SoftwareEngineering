package com.collegeManagementSystem.collegeManagementSystem.dto;

import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDTO {

    private Long id;
    private String title;
    private ProfessorDTO professor;
    private List<StudentDTO> students;

    public SubjectDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }



}
