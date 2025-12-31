package com.example.CollegeManagementSystem.CollegeManagementSystem.dtos;


import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfessorDTO {

    private Long id;

    private String name;

    // Only store related entity IDs to avoid circular references
    private Set<Long> studentIds;
    private Set<Long> subjectIds;
}
