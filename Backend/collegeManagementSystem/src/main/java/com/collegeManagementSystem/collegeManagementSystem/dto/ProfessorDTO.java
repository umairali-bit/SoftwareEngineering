package com.collegeManagementSystem.collegeManagementSystem.dto;


import lombok.Data;

import java.util.List;

@Data
public class ProfessorDTO {

    private Long id;
    private String title;
    private List<SubjectDTO> subjects;
    private List<StudentDTO> students;


}
