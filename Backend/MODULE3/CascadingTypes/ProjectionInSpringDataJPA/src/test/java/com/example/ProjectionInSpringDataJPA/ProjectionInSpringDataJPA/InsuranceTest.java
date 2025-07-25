package com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA;

import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.entity.Insurance;
import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.service.InsuranceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class InsuranceTest {

    @Autowired
    private InsuranceService insuranceService;

    @Test
    public void testAssignInsuranceToPatient(){

        Insurance insurance = new Insurance();
        insurance.setProvider("Aetna");
        insurance.setPolicyNumber("432874HJK");
        insurance.setValidUntil(LocalDate.of(2030,1,1));

        var updatedInsurance = insuranceService.assignInsuranceToPatient(insurance,1L);

        System.out.println(updatedInsurance);

        insuranceService.deletePatient(1L);
    }


}
