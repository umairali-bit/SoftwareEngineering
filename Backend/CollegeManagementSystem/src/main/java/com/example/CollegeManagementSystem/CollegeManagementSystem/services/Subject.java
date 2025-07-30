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
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class Subject {

    private final ProfessorRepository professorRepository;

    private final StudentRepository studentRepository;

    private final SubjectRepository subjectRepository;

    public Subject(ProfessorRepository professorRepository, StudentRepository studentRepository, SubjectRepository subjectRepository) {
        this.professorRepository = professorRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    @Transactional
    public SubjectEntity createNewSubject(SubjectEntity subject, Long professorId, Set<Long> studentIds) {
        ProfessorEntity professor = professorRepository.findById(professorId).orElseThrow();
        Set<StudentEntity> students = studentRepository.findAllById(studentIds).stream().collect(Collectors.toSet());

        subject.setProfessor(professor);
        subject.setStudents(students);

        return subjectRepository.save(subject);
    }
}
