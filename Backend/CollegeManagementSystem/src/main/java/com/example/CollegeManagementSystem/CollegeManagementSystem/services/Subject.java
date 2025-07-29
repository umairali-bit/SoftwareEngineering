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

        ProfessorEntity professor = professorRepository.findById(professorId).orElseThrow(); //transient state

        Set<StudentEntity> students = new HashSet<>();

        for (Long studentId : studentIds) {
            StudentEntity student = studentRepository.findById(studentId).orElseThrow();//persistence

            students.add(student);//persistence state
        }

        subject.setProfessor(professor);
        subject.setStudents(students);



        subjectRepository.save(subject);//save = managed-> persistence

        return subject;

    }
}
