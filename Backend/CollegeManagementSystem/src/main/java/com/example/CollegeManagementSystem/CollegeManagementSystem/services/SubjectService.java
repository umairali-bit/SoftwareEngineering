package com.example.CollegeManagementSystem.CollegeManagementSystem.services;


import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.ProfessorEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.StudentEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.SubjectEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.ProfessorRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.StudentRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.SubjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SubjectService {

    private final ProfessorRepository professorRepository;

    private final StudentRepository studentRepository;

    private final SubjectRepository subjectRepository;

    public SubjectService(ProfessorRepository professorRepository, StudentRepository studentRepository, SubjectRepository subjectRepository) {
        this.professorRepository = professorRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    @Transactional
    public SubjectEntity createNewSubject(SubjectEntity subject, Long professorId, Set<Long> studentIds) {

        // Load professor from DB - this entity is not managed - Persistence state
        ProfessorEntity professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new RuntimeException("Professor not found"));

        // Load students from DB - each student entity is also managed (persistent) after fetch
        Set<StudentEntity> students = new HashSet<>();//transient state
        for (Long studentId : studentIds) {
            StudentEntity student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new RuntimeException("Student not found"));
            students.add(student);
        }

        // Set the professor and students to the new subject entity (subject is in transient state here)
        subject.setProfessor(professor);
        subject.setStudents(students);

        // Save subject - this persists the subject entity and makes it managed/persistent
        return subjectRepository.save(subject);//persistence
    }

    public SubjectEntity getSubjectById(Long subjectId) {

        //Load from DB - Persistence State
        return subjectRepository.findById(subjectId).orElseThrow();

    }

    public List<SubjectEntity> getAllSubjects() {
        // Load from DB — the returned entity is in the Managed (Persistent) state
        return subjectRepository.findAll();
    }






}
