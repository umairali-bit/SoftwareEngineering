package com.example.CollegeManagementSystem.CollegeManagementSystem.dtos;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class StudentDTO {
    private Long id;
    private String name;


    // You can optionally include this if you want admission record details
    private AdmissionRecordDTO admissionRecord;

    private Set<Long> subjectIds;    // For referencing subject IDs only
    private Set<Long> professorIds;  // For referencing professor IDs only
}
