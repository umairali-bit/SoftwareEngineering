package com.example.CollegeManagementSystem.CollegeManagementSystem;


import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.ProfessorEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.StudentEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.SubjectEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.AdmissionRecordRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.ProfessorRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.StudentRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.SubjectRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.services.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

@SpringBootTest
public class ProfessorTest {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private AdmissionRecordRepository admissionRecordRepository;

    @Autowired
    private Subject subjectService;

//    @Test
////    public void testSaveAdmissionRecordWithStudent() {
////        StudentEntity student = new StudentEntity();
////        student.setName("Ali");
////
////        student = studentRepository.save(student);
////
////        System.out.println(student);


    @Test
    public ProfessorEntity createProfessor() {
        ProfessorEntity professor = new ProfessorEntity();
        professor.setTitle("Dr. Jane Doe");
        ProfessorEntity savedProfessor = professorRepository.save(professor);


        return savedProfessor;



    }

    @Test
    public List<StudentEntity> createStudent() {
        StudentEntity student = new StudentEntity();
        StudentEntity student2 = new StudentEntity();
        student.setName("Alice");
        student2.setName("Bob");
        student = studentRepository.save(student);
        student2 = studentRepository.save(student2);


        return List.of(student, student2);

    }


    @Test
    public void testCreateNewSubject() {
        SubjectEntity subject = new SubjectEntity();
        subject.setTitle("Algorithms");

        ProfessorEntity professor = createProfessor();
        Long professorId = createProfessor().getId();


        List<StudentEntity> students = createStudent();

        StudentEntity student = students.get(0);
        StudentEntity student2 = students.get(1);

        Set<Long> studentIds = Set.of(student.getId(), student2.getId());

        SubjectEntity createdSubject = subjectService.createNewSubject(subject, professorId, studentIds);

        System.out.println(createdSubject);
    }

    }









