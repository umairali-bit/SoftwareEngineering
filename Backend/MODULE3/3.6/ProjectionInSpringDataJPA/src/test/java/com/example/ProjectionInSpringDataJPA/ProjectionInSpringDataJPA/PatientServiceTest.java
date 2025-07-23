package com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA;


import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PatientServiceTest {

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void testPatient() {


    }
}
