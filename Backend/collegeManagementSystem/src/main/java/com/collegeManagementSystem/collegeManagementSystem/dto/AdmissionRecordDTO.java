package com.collegeManagementSystem.collegeManagementSystem.dto;

import lombok.Data;

@Data
public class AdmissionRecordDTO {

    private Long id;
    private Integer fees;
    private StudentDTO student;


}
