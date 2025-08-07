package com.example.CollegeManagementSystem.CollegeManagementSystem;


import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.AdmissionRecordDTO;
import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.StudentDTO;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.AdmissionRecordEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.StudentEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.AdmissionRecordRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.ProfessorRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.StudentRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.SubjectRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.services.StudentService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class StudentTest {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AdmissionRecordRepository admissionRecordRepository;

    @Autowired
    private StudentService studentService;


    @Autowired
    private ModelMapper modelMapper;


    @Test
    public void testCreateStudent_WithAdmissionRecord() {

        // prepare Admission record DTO
        AdmissionRecordDTO admissionRecordDTO = AdmissionRecordDTO.builder()
                .admissionDate(LocalDateTime.of(2025,8,8,0,0))
                .fees(20000.00)
                .build();

        // prepare student dto
        StudentDTO studentDTO = StudentDTO.builder()
                .name("Jesse Pinkman")
                .admissionRecord(admissionRecordDTO)
                .build();

        // expected entity after save
        StudentEntity savedEntity = new StudentEntity();
        savedEntity.setId(1L);
        savedEntity.setName("Jesse Pinkman");


        AdmissionRecordEntity admissionRecordEntity = new AdmissionRecordEntity();
        admissionRecordEntity.setId(10L);
        admissionRecordEntity.setAdmissionDate(LocalDateTime.of(2025,8,8,0,0));
        admissionRecordEntity.setFees(20000.00);
        admissionRecordEntity.setStudent(savedEntity);

        savedEntity.setAdmissionRecord(admissionRecordEntity);

        StudentDTO result = studentService.createStudent(studentDTO);

        System.out.println(result);

        AdmissionRecordEntity entity = savedEntity.getAdmissionRecord();
        System.out.println("Fees in entity: " + entity.getFees());

        AdmissionRecordDTO dto = modelMapper.map(entity, AdmissionRecordDTO.class);
        System.out.println("Fees in DTO after map: " + dto.getFees());

        StudentDTO dtos = modelMapper.map(savedEntity, StudentDTO.class);
        System.out.println("Fees in nested admission record: " + dtos.getAdmissionRecord().getFees());

        System.out.println("AdmissionRecord fees before save: " + admissionRecordEntity.getFees());

    }

    @Test
    void testGetStudentById() {
        // create a student and its admission record

        AdmissionRecordEntity admissionRecordEntity = new AdmissionRecordEntity();
        admissionRecordEntity.setAdmissionDate(LocalDateTime.of(2025, 9,9,0,0));
        admissionRecordEntity.setFees(30000.00);


        StudentEntity student = new StudentEntity();
        student.setName("Walter White");
        student.setAdmissionRecord(admissionRecordEntity);
        admissionRecordEntity.setStudent(student); //bidirectional link

        // save to DB
        StudentEntity savedEntity = studentRepository.save(student);
        Long id = savedEntity.getId();

        //Act
        StudentDTO result = studentService.getStudentById(id);

        //Print
        System.out.println("Student ID: " +result.getId());
        System.out.println("Student Name: " +result.getName());
        System.out.println("Student Admission Record: " + result.getAdmissionRecord());







    }






}
