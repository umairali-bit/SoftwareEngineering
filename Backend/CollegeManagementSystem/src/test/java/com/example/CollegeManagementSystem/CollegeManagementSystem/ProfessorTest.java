package com.example.CollegeManagementSystem.CollegeManagementSystem;


import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.StudentDTO;
import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.SubjectDTO;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.ProfessorEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.StudentEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.SubjectEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.AdmissionRecordRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.ProfessorRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.StudentRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.SubjectRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.services.SubjectService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
    private SubjectService subjectService;

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
        // Fetch existing professors and students from DB
        List<ProfessorEntity> professors = professorRepository.findAll();
        List<StudentEntity> students = studentRepository.findAll();

        if (professors.isEmpty() || students.isEmpty()) {
            System.out.println("No professors or students found to create subject.");
            return;
        }

        ProfessorEntity professor = professors.get(0);

        Set<Long> studentIds = students.stream()
                .map(student -> student.getId())
                .collect(Collectors.toSet());

        // Prepare SubjectDTO
        SubjectDTO newSubject = new SubjectDTO();
        newSubject.setName("Test Subject");
        newSubject.setProfessorId(professor.getId());
        newSubject.setStudents(studentIds); // assuming your DTO has 'students' as Set<Long>

        // Call service method
        SubjectDTO createdSubject = subjectService.createSubject(newSubject);

        // Assertions / Prints
        System.out.println("Created Subject: " + createdSubject);
        System.out.println("Assigned Professor ID: " + createdSubject.getProfessorId());
        System.out.println("Assigned Professor Name: " + createdSubject.getProfessorName());
        System.out.println("Assigned Students: " + createdSubject.getStudents());
    }

    @Test
    void testGetSubjectById() {

        ProfessorEntity professor = new ProfessorEntity();
        professor.setName("Dr. Smith");
        professor = professorRepository.save(professor);

        // Create and save students
        StudentEntity student1 = new StudentEntity();
        student1.setName("Alice");
        student1 = studentRepository.save(student1);

        StudentEntity student2 = new StudentEntity();
        student2.setName("Bob");
        student2 = studentRepository.save(student2);

        SubjectEntity subject = new SubjectEntity();
        subject.setName("Math");
        subject.setProfessor(professor);

        Set<StudentEntity> students = new HashSet<>();
        students.add(student1);
        students.add(student2);
        subject.setStudents(students);

        subject = subjectRepository.save(subject);

        SubjectDTO result = subjectService.getSubjectById(subject.getId());
        System.out.println(result);

    }

    @Test
    void testGetAllSubjects() {

        List<SubjectDTO> subjects = subjectService.getAllSubjects();
        for (SubjectDTO s : subjects) {
            System.out.println(s);
        }
    }

    @Test
    @Transactional
    public void testUpdateSubject() {
        // Step 1: Create and save an initial professor
        ProfessorEntity originalProfessor = new ProfessorEntity();
        originalProfessor.setName("Dr. Original Professor");
        originalProfessor = professorRepository.save(originalProfessor);

        // Step 2: Create and save an initial subject linked to original professor
        SubjectEntity subject = new SubjectEntity();
        subject.setName("Original Subject");
        subject.setProfessor(originalProfessor);
        subject = subjectRepository.save(subject);

        // Step 3: Create and save a new professor for update
        ProfessorEntity newProfessor = new ProfessorEntity();
        newProfessor.setName("Dr. New Professor");
        newProfessor = professorRepository.save(newProfessor);

        // Step 4: Create and save some students
        StudentEntity student1 = new StudentEntity();
        student1.setName("Alice");
        student1 = studentRepository.save(student1);

        StudentEntity student2 = new StudentEntity();
        student2.setName("Bob");
        student2 = studentRepository.save(student2);

        Set<Long> updatedStudentIds = new HashSet<>();
        updatedStudentIds.add(student1.getId());
        updatedStudentIds.add(student2.getId());

        // Step 5: Prepare updated SubjectDTO
        SubjectDTO updatedSubjectDTO = new SubjectDTO();
        updatedSubjectDTO.setName("Updated Subject Name");
        updatedSubjectDTO.setProfessorId(newProfessor.getId());
        updatedSubjectDTO.setStudents(updatedStudentIds);

        // Step 6: Call the updateSubject service method
        SubjectDTO updatedSubject = subjectService.updateSubject(subject.getId(), updatedSubjectDTO);

        // Step 7: Print updated subject DTO
        System.out.println("Updated Subject DTO: " + updatedSubject);

    }

}









