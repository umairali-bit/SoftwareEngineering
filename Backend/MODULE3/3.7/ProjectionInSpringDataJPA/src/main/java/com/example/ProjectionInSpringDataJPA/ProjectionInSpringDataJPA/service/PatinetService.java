package com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.service;


import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.repository.PatientRepository;
import org.springframework.stereotype.Service;

@Service
public class PatinetService {

    public final PatientRepository patientRepository;

    public PatinetService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public testPatientTransaction() {

    }
}
