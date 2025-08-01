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
        professor.getSubjects().add(subject); // Attach subjects to professor

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


    @Transactional
    public List<SubjectDTO> getAllSubjects() {
        // Load from DB — the returned entity is in the Managed (Persistent) state
        List<SubjectEntity> subjectEntities = subjectRepository.findAll();

        return subjectEntities
                .stream()
                .map(subjectEntity -> modelMapper.map(subjectEntity, SubjectDTO.class))
                .collect(Collectors.toList());
    }


    @Transactional
    public SubjectDTO updateSubject (Long subjectId, SubjectDTO subjectDTO) {
        //1. Load the existing entity (Persistence State)
        SubjectEntity subjectEntity = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found with ID: " + subjectId));

        //2. Map incoming DTO into the existing entity
        modelMapper.map(subjectDTO, subjectEntity);

        //3. Handle associations explicitly
        if (subjectDTO.getProfessorId() != null) {
            ProfessorEntity professor = professorRepository.findById(subjectDTO.getProfessorId())
                    .orElseThrow(() -> new RuntimeException("Professor not found"));
            subjectEntity.setProfessor(professor);
        }

        //4. Handle Student updates if needed
        if (subjectDTO.getStudents() != null) {
            Set<StudentEntity> students = subjectDTO.getStudents().stream()
                    .map(idVal -> studentRepository.findById(idVal)
                            .orElseThrow(() -> new RuntimeException("Student not found with ID: " + idVal)))
                    .collect(Collectors.toSet());

            subjectEntity.setStudents(students);
        }

        //5. Save updated entity and return DTO
        SubjectEntity updatedSubject = subjectRepository.save(subjectEntity);
        SubjectDTO updatedDTO = modelMapper.map(updatedSubject, SubjectDTO.class);

        // Manually set students IDs in DTO
        Set<Long> studentIds = updatedSubject.getStudents().stream()
                .map(StudentEntity::getId)
                .collect(Collectors.toSet());
        updatedDTO.setStudents(studentIds);
        return updatedDTO;
    }

    @Transactional
    public SubjectDTO patchUpdateSubject(Long subjectId, SubjectDTO subjectDTO){

        //1.Load the existing subject (Persistence State)
        SubjectEntity subjectEntity = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found with ID: " + subjectId));

        //2. Update only non-null simple fields
        if (subjectDTO.getName() != null) {
            subjectEntity.setName(subjectDTO.getName());
        }

        //3. Update Professor if professorId is present
        if (subjectDTO.getProfessorId() != null) {
            ProfessorEntity professor = professorRepository.findById(subjectDTO.getProfessorId())
                    .orElseThrow(() -> new RuntimeException("Professor not found with ID: "
                            + subjectDTO.getProfessorId()));
            subjectEntity.setProfessor(professor);

        }

        //4. Update student if provided
        if (subjectDTO.getStudents() != null) {
            Set<StudentEntity> studentEntities = subjectDTO.getStudents().stream()
                    .map(studentId -> studentRepository.findById(studentId)
                            .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId)))
                    .collect(Collectors.toSet());

            subjectEntity.setStudents(studentEntities);

        }

        //5. Save a return updated entity
        SubjectEntity updatedSubject = subjectRepository.save(subjectEntity);

        //6. Map back to DTO manually especially, professor, student
        SubjectDTO updatedDTO = new SubjectDTO();
        updatedDTO.setId(updatedSubject.getId());
        updatedDTO.setName(updatedSubject.getName());
        updatedDTO.setProfessorId(updatedSubject.getProfessor().getId());
        updatedDTO.setProfessorName(updatedSubject.getProfessor().getName());
        updatedDTO.setStudents(
                updatedSubject.getStudents().stream()
                        .map(studentEntity -> studentEntity.getId())
                        .collect(Collectors.toSet())
        );

        return updatedDTO;

    }

    public boolean deleteSubjectById(Long subjectId) {
        if (!subjectRepository.existsById(subjectId)) {
            throw new RuntimeException("Subject does not exist with ID: " + subjectId);
        }

        subjectRepository.deleteById(subjectId);
        return true;
    }







}
