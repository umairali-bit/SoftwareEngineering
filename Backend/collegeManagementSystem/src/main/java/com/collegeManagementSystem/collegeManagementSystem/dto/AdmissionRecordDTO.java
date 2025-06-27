package com.collegeManagementSystem.collegeManagementSystem.dto;

import lombok.Data;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;

@Data
public class AdmissionRecordDTO {

    private Long id;
    @Max(value = 1000, message = "Fees cannot be greater than 1000")
    @Min(value = 500, message = "Fees cannot be less than 500")
    private Integer fees;
    private StudentDTO student;
    private LocalDateTime admissionDateTime = LocalDateTime.now();


}
