package com.example.CollegeManagementSystem.CollegeManagementSystem.dtos;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubjectDTO {
    private Long id;
    private String name;
    private Long professorId;
    private String professorName;
    private Set<Long> students = new HashSet<>();
}
