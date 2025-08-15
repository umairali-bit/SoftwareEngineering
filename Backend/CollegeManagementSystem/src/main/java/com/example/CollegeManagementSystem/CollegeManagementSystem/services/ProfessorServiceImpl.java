package com.example.CollegeManagementSystem.CollegeManagementSystem.services;

import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.AdmissionRecordRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.ProfessorRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.StudentRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.SubjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;

@Service
public class ProfessorServiceImpl implements ProfessorService{

    private final SubjectRepository subjectRepository;

    private final StudentRepository studentRepository;

    private final ProfessorRepository professorRepository;

    private final AdmissionRecordRepository admissionRecordRepository;

    private final ModelMapper modelMapper;


    public ProfessorServiceImpl(SubjectRepository subjectRepository, StudentRepository studentRepository,
                                ProfessorRepository professorRepository, AdmissionRecordRepository admissionRecordRepository,
                                ModelMapper modelMapper) {
        this.subjectRepository = subjectRepository;
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
        this.admissionRecordRepository = admissionRecordRepository;
        this.modelMapper = modelMapper;
    }


}
