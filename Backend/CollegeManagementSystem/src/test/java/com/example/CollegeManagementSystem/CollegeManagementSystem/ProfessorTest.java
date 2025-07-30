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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Test
    public void testProfessor() {
        List<ProfessorEntity> professor = professorRepository.findAll();
        for (ProfessorEntity p : professor) {
            System.out.println(p);
        }
    }

    @Test
    public void testStudents() {
        List<StudentEntity> students = studentRepository.findAll();
        for (StudentEntity s : students) {
            System.out.println(s);
        }

    }

    @Test
    public void testCreateSubject() {
        // Assume you already have professors and students saved in DB
        List<ProfessorEntity> professors = professorRepository.findAll();
        List<StudentEntity> students = studentRepository.findAll();

        if (professors.isEmpty() || students.isEmpty()) {
            System.out.println("No professors or students found to create subject.");
            return;
        }

        ProfessorEntity professor = professors.get(0);

        Set<Long> studentIds = students.stream()
                .map(StudentEntity::getId)
                .collect(Collectors.toSet());

        SubjectEntity newSubject = new SubjectEntity();
        newSubject.setName("Test Subject");

        SubjectEntity createdSubject = subjectService.createNewSubject(newSubject, professor.getId(), studentIds);

        System.out.println("Created Subject: " + createdSubject);
        System.out.println("Assigned Professor: " + createdSubject.getProfessor());
        System.out.println("Assigned Students: " + createdSubject.getStudents());
    }
}









