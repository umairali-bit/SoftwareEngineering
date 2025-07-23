package com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA;


import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.dto.PatientInfo;
import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.entity.Patient;
import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PatientServiceTest {

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void testPatient() {

        List<PatientInfo> patientList = patientRepository.getPatientInfo();

        for (PatientInfo p : patientList) {
            System.out.println(p);
        }


    }
}
