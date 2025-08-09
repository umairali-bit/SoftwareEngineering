package com.example.CollegeManagementSystem.CollegeManagementSystem;


import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.AdmissionRecordDTO;
import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.StudentDTO;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.AdmissionRecordEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.ProfessorEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.StudentEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.SubjectEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.AdmissionRecordRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.ProfessorRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.StudentRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.SubjectRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.services.StudentService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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

    @Test
    void testUpdateStudent() {
        //create AdmissionRecord, student, subject and professor

        ProfessorEntity professor = new ProfessorEntity();
        professor.setName("Mr. Smith");
        professorRepository.save(professor);

        SubjectEntity subject = new SubjectEntity();
        subject.setName("Calculus");
        subject.setProfessor(professor);
        subjectRepository.save(subject);

        StudentEntity student = new StudentEntity();
        student.setName("Original Name");
        student.setProfessors(Set.of(professor));
        student.setSubjects(Set.of(subject));
        studentRepository.save(student);

        AdmissionRecordEntity admissionRecord = new AdmissionRecordEntity();
        admissionRecord.setAdmissionDate(LocalDateTime.of(2025, 8, 8, 0, 0));
        admissionRecord.setFees(30000.00);
        admissionRecord.setStudent(student);

        admissionRecordRepository.save(admissionRecord);

        student.setAdmissionRecord(admissionRecord);
        studentRepository.save(student);



        // Prepare DTO
        StudentDTO updatedDTO = StudentDTO.builder()
                .name("Hank Schradar")
                .admissionRecord(AdmissionRecordDTO.builder()
                        .admissionDate(LocalDateTime.of(2025, 9,9,0,0))
                        .fees(2500.00)
                        .build())
                .subjectIds(Set.of(subject.getId()))
                .professorIds(Set.of(professor.getId()))
                .build();


        // Call updatedStudent
        StudentDTO updatedStudent = studentService.updateStudent(student.getId(), updatedDTO);
        System.out.println("Updated Student ID: " + updatedStudent.getId());
        System.out.println("Student Name: " + updatedStudent.getName());
        System.out.println("Student Admission Record: " + updatedStudent.getAdmissionRecord());


    }

    @Test
    void deleteStudent() {
        StudentEntity student = new StudentEntity();
        student.setName("Test Student");
        student = studentRepository.save(student);


        Long studentId = student.getId();

        // Act - delete student
        studentService.deleteStudent(studentId);

        // Check if student still exists
        boolean exists = studentRepository.existsById(studentId);

        // Print the result
        if (exists) {
            System.out.println("Student was NOT deleted.");
        } else {
            System.out.println("Student deleted successfully.");
        }
    }


    @Test
    void patchExistingStudentsAdmissionRecords() {
        // Retrieve existing students by their IDs
        StudentEntity alice = studentRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Student 1 not found"));

        StudentEntity brian = studentRepository.findById(2L)
                .orElseThrow(() -> new RuntimeException("Student 2 not found"));

        StudentEntity chloe = studentRepository.findById(3L)
                .orElseThrow(() -> new RuntimeException("Student 3 not found"));

        // Create AdmissionRecordDTOs to patch
        AdmissionRecordDTO aliceAdmission = AdmissionRecordDTO.builder()
                .admissionDate(LocalDateTime.of(2025, 8, 1, 0, 0))
                .fees(15000.00)
                .build();

        AdmissionRecordDTO brianAdmission = AdmissionRecordDTO.builder()
                .admissionDate(LocalDateTime.of(2025, 8, 2, 0, 0))
                .fees(16000.00)
                .build();

        AdmissionRecordDTO chloeAdmission = AdmissionRecordDTO.builder()
                .admissionDate(LocalDateTime.of(2025, 8, 3, 0, 0))
                .fees(15500.00)
                .build();

        // Patch DTOs
        StudentDTO alicePatch = StudentDTO.builder()
                .admissionRecord(aliceAdmission)
                .build();

        StudentDTO brianPatch = StudentDTO.builder()
                .admissionRecord(brianAdmission)
                .build();

        StudentDTO chloePatch = StudentDTO.builder()
                .admissionRecord(chloeAdmission)
                .build();

        // Patch existing students by their IDs
        StudentDTO updatedAlice = studentService.patchStudent(alice.getId(), alicePatch);
        StudentDTO updatedBrian = studentService.patchStudent(brian.getId(), brianPatch);
        StudentDTO updatedChloe = studentService.patchStudent(chloe.getId(), chloePatch);

        // Print updated info
        System.out.println("Updated Alice: " + updatedAlice);
        System.out.println("Updated Brian: " + updatedBrian);
        System.out.println("Updated Chloe: " + updatedChloe);
    }

}









