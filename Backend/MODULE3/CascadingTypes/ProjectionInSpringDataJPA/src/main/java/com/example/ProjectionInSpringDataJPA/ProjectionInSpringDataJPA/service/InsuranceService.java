package com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.service;


import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.entity.Insurance;
import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.entity.Patient;
import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.repository.InsuranceRepository;
import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.repository.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;
    private final PatientRepository patientRepository;

    public InsuranceService(InsuranceRepository insuranceRepository, PatientRepository patientRepository) {
        this.insuranceRepository = insuranceRepository;
        this.patientRepository = patientRepository;
    }
    @Transactional
    public Insurance assignInsuranceToPatient(Insurance insurance, Long patientId) {

        Patient patient = patientRepository.findById(patientId).orElseThrow();
        patient.setInsurance(insurance);
        insurance.setPatient(patient);

        return insurance;

    }

    public void deletePatient(Long patientId) {

        patientRepository.findById(patientId).orElseThrow();

        patientRepository.deleteById(patientId);


    }


}
