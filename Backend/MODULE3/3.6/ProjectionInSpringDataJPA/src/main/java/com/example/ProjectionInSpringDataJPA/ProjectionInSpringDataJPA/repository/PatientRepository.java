package com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.repository;

import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByEmailContaining(String d);
}
