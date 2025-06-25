package com.collegeManagementSystem.collegeManagementSystem.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class StudentDTO {

    private Long id;
    private String name;
    private List<ProfessorDTO> professors = new ArrayList<>();
    private List<SubjectDTO> subjects = new ArrayList<>();

    private int professorCount;
    private int subjectCount;


    public StudentDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }


}
