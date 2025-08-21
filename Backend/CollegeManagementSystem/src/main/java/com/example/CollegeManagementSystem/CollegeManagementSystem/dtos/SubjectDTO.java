package com.example.CollegeManagementSystem.CollegeManagementSystem.dtos;


import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubjectDTO {
    private Long id;
    private String name;
    private Long professorId;
    private String professorName;
  //  private Set<Long> studentIds;
  //  private Map<Long, String> students;

    private List<StudentSummaryDTO> students;

}
