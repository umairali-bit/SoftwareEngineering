package com.example.CollegeManagementSystem.CollegeManagementSystem.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDTO {
    private Long id;
    private String name;
    private Long professorId;
    private String professorName;
    private Set<Long> studentIds;
}
