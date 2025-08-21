package com.example.CollegeManagementSystem.CollegeManagementSystem.dtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubjectDTO {
    private Long id;
    private String name;
    private Long professorId;
    private String professorName;
    private Set<Long> students;
}
