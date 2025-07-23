package com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.repository;

import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.dto.BloodGroupStats;
import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.dto.PatientInfo;
import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.dto.PatientInfoImpl;
import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query("select p.id as id, p.name as name, p.email as email from Patient p")
    List<PatientInfo> getPatientInfo();


    @Query("select new com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.dto.PatientInfoImpl(p.id, p.name) " +
            "from Patient p")
    List<PatientInfoImpl> getPatientInfoConceret();


    @Query("select new com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.dto.BloodGroupStats(p.bloodGroup," +
            "COUNT(p)) from Patient p group by p.bloodGroup order by COUNT(p)")
    List<BloodGroupStats> getBloodGroupStats();
}
