package com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA;

import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.entity.Appointment;
import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.entity.Insurance;
import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.service.AppointmentService;
import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.service.InsuranceService;
import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class InsuranceTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private InsuranceService insuranceService;

    @Autowired
    private AppointmentService appointmentService;

    @Test
    public void testAssignInsuranceToPatient(){

        Insurance insurance = new Insurance();
        insurance.setProvider("Aetna");
        insurance.setPolicyNumber("432874HJK");
        insurance.setValidUntil(LocalDate.of(2030,1,1));

        var updatedInsurance = insuranceService.assignInsuranceToPatient(insurance,1L);

        System.out.println(updatedInsurance);

        patientService.deletePatient(1L);
    }

    @Test
    public void testCreateAppointment() {

        Appointment appointment = new Appointment();
        appointment.setAppointmentTime(LocalDateTime.of(2025,8,1,14,0,0));
        appointment.setReason("Routine checkup");

        var updatedAppointment = appointmentService.createNewAppointment(appointment,1L,
                                            2L);

        System.out.println(updatedAppointment);

//        patientService.deletePatient(1L);

        var patient = insuranceService.removeInsuranceOfPatient(1L);
        System.out.println(patient);






    }


}
