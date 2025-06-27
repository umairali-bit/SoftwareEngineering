package com.collegeManagementSystem.collegeManagementSystem.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdmissionRecordDTO {

    private Long id;
    private Integer fees;
    private StudentDTO student;
    private LocalDateTime admissionDateTime = LocalDateTime.now();


}
