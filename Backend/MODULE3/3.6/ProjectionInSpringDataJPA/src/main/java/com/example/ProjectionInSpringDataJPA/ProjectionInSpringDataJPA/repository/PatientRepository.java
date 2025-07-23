package com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.repository;

import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.dto.PatientInfo;
import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query("select p.id, p.name, p.email from Patient p")
    List<PatientInfo> getPatientInfo();
}
