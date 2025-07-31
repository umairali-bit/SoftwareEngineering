package com.example.CollegeManagementSystem.CollegeManagementSystem.dtos;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdmissionRecordDTO {

    private Long id;

    private Double fees;

    private Long studentId;

    private String studentName;



}
