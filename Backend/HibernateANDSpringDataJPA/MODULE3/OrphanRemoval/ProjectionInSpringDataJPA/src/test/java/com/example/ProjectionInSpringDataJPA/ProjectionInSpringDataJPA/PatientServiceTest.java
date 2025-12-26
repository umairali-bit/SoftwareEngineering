package com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA;


import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.dto.BloodGroupStats;
import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.dto.PatientInfo;
import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.dto.PatientInfoImpl;
import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.entity.Patient;
import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.repository.PatientRepository;
import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PatientServiceTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void testPatient() {

        // List<Patient> patientList = patientRepository.findAll();

        List<PatientInfo> patientList = patientRepository.getPatientInfo();

        for (PatientInfo p : patientList) {
            System.out.println(p);
        }

        List<PatientInfoImpl> patientInfos = patientRepository.getPatientInfoConceret();

        for (PatientInfoImpl a : patientInfos) {
            System.out.println(a);
        }

        List<BloodGroupStats> bloodGroupStats = patientRepository.getBloodGroupStats();

        for (var n : bloodGroupStats) {
            System.out.println(n);
        }

        int rowsAffected = patientRepository.updatePatientNameWithId("Umair Ali", 1L);
        System.out.println(rowsAffected);

        patientService.testPatientTransaction();

        List<Patient> patient = patientRepository.getPatientsWithAppointments();

        for (var p : patient) {
            System.out.println(p);

        }





    }
}
