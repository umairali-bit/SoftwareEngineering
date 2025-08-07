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
import org.springframework.test.annotation.Commit;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
        AdmissionRecordDTO admissionRecord = AdmissionRecordDTO.builder()
                .admissionDate(LocalDateTime.of(2025,8,8,0,0))
                .fees(20000.00)
                .build();

        // prepare student DTO
        StudentDTO student = StudentDTO.builder()
                .name("Jesse Pinkman")
                .admissionRecord(admissionRecord)
                .build();


        // save to DB
        StudentDTO createdStudent= studentService.createStudent(student);



        // Print results
        System.out.println("Created Student ID: " + createdStudent.getId());
        System.out.println("Student Name: " + createdStudent.getName());
        System.out.println("Admission Date: " + createdStudent.getAdmissionRecord().getAdmissionDate());
        System.out.println("Admission Fees: " + createdStudent.getAdmissionRecord().getFees());

    }

    @Test
    @Commit //persists Ben in DB
    void testGetStudentById() {
        // create a student and its admission record

        AdmissionRecordEntity admissionRecordEntity = new AdmissionRecordEntity();
        admissionRecordEntity.setAdmissionDate(LocalDateTime.of(2025, 9,9,0,0));
        admissionRecordEntity.setFees(30000.00);


        StudentEntity student = new StudentEntity();
        student.setName("Ben");
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

    @Test
    void testGetAllStudents() {
        List<StudentDTO> studentDTOList = studentService.getAllStudents();
        System.out.println("Student List: " + studentDTOList);
    }





}
