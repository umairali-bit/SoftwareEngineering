package com.example.CollegeManagementSystem.CollegeManagementSystem.dtos;


import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubjectDTO {
    private Long id;

    @NotBlank(message = "Subject name must not be blank")
    private String name;

    private Long professorId;
    private String professorName;
  //  private Set<Long> studentIds;
  //  private Map<Long, String> students;

    private List<StudentSummaryDTO> students;

}
