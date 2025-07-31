package com.example.CollegeManagementSystem.CollegeManagementSystem.services;


import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.SubjectDTO;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.ProfessorEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.StudentEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.SubjectEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.ProfessorRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.StudentRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.SubjectRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    private final ProfessorRepository professorRepository;

    private final StudentRepository studentRepository;

    private final SubjectRepository subjectRepository;

    private final ModelMapper modelMapper;

    public SubjectService(ProfessorRepository professorRepository, StudentRepository studentRepository, SubjectRepository subjectRepository, ModelMapper modelMapper) {
        this.professorRepository = professorRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public SubjectDTO createSubject(SubjectDTO subjectDTO) {
        // --- Transient State ---
        // Map DTO to Entity (subject is in transient state)
        SubjectEntity subject = modelMapper.map(subjectDTO, SubjectEntity.class);

        // --- Persistent State for Professor ---
        ProfessorEntity professor = professorRepository.findById(subjectDTO.getProfessorId())
                .orElseThrow(() -> new RuntimeException("Professor not found"));
        subject.setProfessor(professor); // Attach managed professor

        // --- Persistent State for Students ---
        Set<StudentEntity> managedStudents = new HashSet<>();
        for (Long studentId : subjectDTO.getStudents()) {
            StudentEntity student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new RuntimeException("Student not found"));
            managedStudents.add(student);
        }
        subject.setStudents(managedStudents); // Attach managed students

        // --- Persist the Subject Entity ---
        SubjectEntity savedSubject = subjectRepository.save(subject);

        // --- Prepare DTO Response ---
        SubjectDTO savedDTO = new SubjectDTO();
        savedDTO.setId(savedSubject.getId());
        savedDTO.setName(savedSubject.getName());
        savedDTO.setProfessorId(savedSubject.getProfessor().getId());
        savedDTO.setProfessorName(savedSubject.getProfessor().getName());

        // Add student IDs
        Set<Long> studentIds = new HashSet<>();
        for (StudentEntity s : savedSubject.getStudents()) {
            studentIds.add(s.getId());
        }
        savedDTO.setStudents(studentIds);

        return savedDTO;
    }

    @Transactional
    public SubjectDTO getSubjectById(Long subjectId) {
        SubjectEntity subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        SubjectDTO dto = new SubjectDTO();
        dto.setId(subject.getId());
        dto.setName(subject.getName());

        if(subject.getProfessor() != null) {
            dto.setProfessorId(subject.getProfessor().getId());
            dto.setProfessorName(subject.getProfessor().getName());
        }

        if (subject.getStudents() != null) {
            Set<Long> studentsIds = subject.getStudents().stream()
                    .map(s -> s.getId())
                    .collect(Collectors.toSet());
            dto.setStudents(studentsIds);

        }

        return dto;
    }



    public List<SubjectDTO> getAllSubjects() {
        // Load from DB — the returned entity is in the Managed (Persistent) state
        List<SubjectEntity> subjectEntities = subjectRepository.findAll();

        return subjectEntities
                .stream()
                .map(subjectEntity -> modelMapper.map(subjectEntity, SubjectDTO.class))
                .collect(Collectors.toList());
    }






}
