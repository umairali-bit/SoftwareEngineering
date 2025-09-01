package com.example.CollegeManagementSystem.CollegeManagementSystem.dtos;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AdmissionRecordDTO {

    private Long id;

    private Double fees;

    private LocalDateTime admissionDate;



}
