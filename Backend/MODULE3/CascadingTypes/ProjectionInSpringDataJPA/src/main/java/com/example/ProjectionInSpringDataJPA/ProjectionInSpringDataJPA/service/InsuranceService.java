package com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.service;


import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.repository.InsuranceRepository;
import org.springframework.stereotype.Service;

@Service
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;

    public InsuranceService(InsuranceRepository insuranceRepository) {
        this.insuranceRepository = insuranceRepository;
    }
}
