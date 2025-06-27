package com.collegeManagementSystem.collegeManagementSystem.services;

import com.collegeManagementSystem.collegeManagementSystem.dto.*;
import com.collegeManagementSystem.collegeManagementSystem.entities.*;
import com.collegeManagementSystem.collegeManagementSystem.repositories.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final SubjectRepository subjectRepository;
    private final ModelMapper modelMapper;

    public StudentServiceImpl(StudentRepository studentRepository,
                              ProfessorRepository professorRepository,
                              SubjectRepository subjectRepository,
                              ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
        this.subjectRepository = subjectRepository;
        this.modelMapper = modelMapper;
    }

    private void studentExistsById(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new NoSuchElementException("Student not found by id: " + id);
        }
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        List<StudentEntity> students = studentRepository.findAllWithProfessorsAndSubjects();

        return students.stream()
                .map(this::mapStudentEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<StudentDTO> getStudentById(Long id) {
        studentExistsById(id);
        return studentRepository.findById(id).map(this::mapStudentEntityToDTO);
    }

    private StudentDTO mapStudentEntityToDTO(StudentEntity student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());

        dto.setProfessors(
                student.getProfessors().stream()
                        .map(this::mapProfessorEntityToDTOWithoutStudents) // avoid recursion
                        .collect(Collectors.toList())
        );

        dto.setSubjects(
                student.getSubjects().stream()
                        .map(this::mapSubjectEntityToDTOWithoutStudents)
                        .collect(Collectors.toList())
        );

        dto.setSubjectCount(dto.getSubjects().size());

        return dto;
    }

    // Map professor but without students to avoid infinite recursion
    private ProfessorDTO mapProfessorEntityToDTOWithoutStudents(ProfessorEntity professor) {
        ProfessorDTO dto = new ProfessorDTO();
        dto.setId(professor.getId());
        dto.setTitle(professor.getTitle());
        dto.setName(professor.getName());

        dto.setSubjects(
                professor.getSubjects().stream()
                        .map(this::mapSubjectEntityToDTOWithoutStudents)
                        .collect(Collectors.toList())
        );

        // Do NOT set students here to avoid recursion

        return dto;
    }

    // Map subject without students (to avoid recursion)
    private SubjectDTO mapSubjectEntityToDTOWithoutStudents(SubjectEntity subject) {
        SubjectDTO dto = new SubjectDTO();
        dto.setId(subject.getId());
        dto.setTitle(subject.getTitle());

        if(subject.getProfessor() != null) {
            dto.setProfessor(new ProfessorDTO(subject.getProfessor().getId(),
                    subject.getProfessor().getTitle(),
                    subject.getProfessor().getName()));
        }

        dto.setStudents(Collections.emptyList()); // omit students to avoid recursion
        dto.setStudentCount(subject.getStudents() != null ? subject.getStudents().size() : 0);

        return dto;
    }

    @Transactional
    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setName(studentDTO.getName());

        if (studentDTO.getProfessors() != null) {
            for (ProfessorDTO profDTO : studentDTO.getProfessors()) {
                ProfessorEntity professorEntity = professorRepository.findById(profDTO.getId())
                        .orElseThrow(() -> new NoSuchElementException("Professor not found by id: " + profDTO.getId()));
                studentEntity.getProfessors().add(professorEntity);
                professorEntity.getStudents().add(studentEntity);
            }
        }

        if (studentDTO.getSubjects() != null) {
            for (SubjectDTO subjDTO : studentDTO.getSubjects()) {
                SubjectEntity subjectEntity = subjectRepository.findById(subjDTO.getId())
                        .orElseThrow(() -> new NoSuchElementException("Subject not found by id: " + subjDTO.getId()));
                studentEntity.getSubjects().add(subjectEntity);
                subjectEntity.getStudents().add(studentEntity);
            }
        }

        StudentEntity saved = studentRepository.save(studentEntity);
        return mapStudentEntityToDTO(saved);
    }

    @Transactional
    @Override
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        studentExistsById(id);
        return studentRepository.findById(id).map(student -> {
            student.setName(studentDTO.getName());
            StudentEntity updated = studentRepository.save(student);
            return mapStudentEntityToDTO(updated);
        }).orElseThrow(() -> new NoSuchElementException("Student not found with id: " + id));
    }

    @Override
    public List<SubjectDTO> getSubjectsByStudentId(Long studentId) {
        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NoSuchElementException("Student not found by id: " + studentId));

        return student.getSubjects().stream()
                .map(this::mapSubjectEntityToDTOWithoutStudents)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public boolean deleteStudent(Long id) {
        studentExistsById(id);
        studentRepository.deleteById(id);
        return true;
    }
}
