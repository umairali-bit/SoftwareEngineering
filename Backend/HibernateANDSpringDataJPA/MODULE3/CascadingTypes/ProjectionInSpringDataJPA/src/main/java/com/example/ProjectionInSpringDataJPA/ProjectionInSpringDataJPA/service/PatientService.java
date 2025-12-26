package com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.service;


import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.entity.Patient;
import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.repository.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    public final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Transactional
    public void testPatientTransaction() {

        Patient p1 = patientRepository.findById(1L).orElseThrow();
        Patient p2 = patientRepository.findById(1L).orElseThrow();

        System.out.println(p1 + " " + p2);
        System.out.println(p1 == p2);

        p1.setName("Random Name");



    }

    public void deletePatient(Long patientId) {

        patientRepository.findById(patientId).orElseThrow();

        patientRepository.deleteById(patientId);


    }
}
