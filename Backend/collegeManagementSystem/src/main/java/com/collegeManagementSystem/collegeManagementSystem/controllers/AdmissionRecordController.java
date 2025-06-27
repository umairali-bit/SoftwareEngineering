package com.collegeManagementSystem.collegeManagementSystem.controllers;


import com.collegeManagementSystem.collegeManagementSystem.dto.AdmissionRecordDTO;
import com.collegeManagementSystem.collegeManagementSystem.services.AdmissionRecordService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admissions")
public class AdmissionRecordController {

    private final AdmissionRecordService admissionRecordService;

    public AdmissionRecordController(AdmissionRecordService admissionRecordService) {
        this.admissionRecordService = admissionRecordService;
    }

    @GetMapping
    public ResponseEntity<List<AdmissionRecordDTO>> getAllAdmissions() {
        List<AdmissionRecordDTO> admissions = admissionRecordService.getAllAdmissions();
        return ResponseEntity.ok(admissions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdmissionRecordDTO> getAdmissionById(@PathVariable Long id) {
        AdmissionRecordDTO admission = admissionRecordService.getAdmissionById(id);
        return ResponseEntity.ok(admission);
    }

    @PostMapping
    public ResponseEntity<AdmissionRecordDTO> createAdmission(@Valid @RequestBody AdmissionRecordDTO admissionRecordDTO) {
        AdmissionRecordDTO created = admissionRecordService.createAdmissionRecord(admissionRecordDTO);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdmissionRecordDTO> updateAdmission(@Valid @PathVariable Long id,
                                                              @RequestBody AdmissionRecordDTO admissionRecordDTO) {
        AdmissionRecordDTO updated = admissionRecordService.updateAdmission(id, admissionRecordDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<AdmissionRecordDTO> getAdmissionByStudentId(@PathVariable Long studentId) {
        AdmissionRecordDTO record = admissionRecordService.getAdmissionRecordByStudentId(studentId);
        return ResponseEntity.ok(record);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteAdmission(@PathVariable Long id) {
        boolean deleted = admissionRecordService.deleteAdmissionRecord(id);
        return ResponseEntity.ok(deleted);
    }
}
