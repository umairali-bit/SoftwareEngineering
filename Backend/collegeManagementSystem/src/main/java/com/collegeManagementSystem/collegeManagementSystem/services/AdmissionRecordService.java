package com.collegeManagementSystem.collegeManagementSystem.services;

import com.collegeManagementSystem.collegeManagementSystem.dto.AdmissionRecordDTO;

import java.util.List;

public interface AdmissionRecordService {

    List<AdmissionRecordDTO> getAllAdmissions();
    AdmissionRecordDTO getAdmissionById(Long id);

    AdmissionRecordDTO createAdmissionRecord (AdmissionRecordDTO admissionRecordDTO);
    AdmissionRecordDTO updateAdmission(Long id, AdmissionRecordDTO admissionRecordDTO);

    AdmissionRecordDTO getAdmissionRecordByStudentId(Long studentId);

    boolean deleteAdmissionRecord(Long id);
}
