package com.collegeManagementSystem.collegeManagementSystem.services;


import com.collegeManagementSystem.collegeManagementSystem.dto.ProfessorDTO;
import com.collegeManagementSystem.collegeManagementSystem.dto.StudentDTO;
import com.collegeManagementSystem.collegeManagementSystem.dto.SubjectDTO;
import com.collegeManagementSystem.collegeManagementSystem.entities.StudentEntity;
import com.collegeManagementSystem.collegeManagementSystem.repositories.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {


    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;


    public StudentServiceImpl(StudentRepository studentRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;

    }

    public void studentExistsById(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new NoSuchElementException("Student not found by the id: " + id);
        }
    }


    @Override
    public List<StudentDTO> getAllStudents() {
        List<StudentEntity> students = studentRepository.findAll();
        return students.stream()
                .map(student -> modelMapper.map(student, StudentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<StudentDTO> getStudentById(Long id) {
        studentExistsById(id);
        Optional<StudentEntity> studentEntity = studentRepository.findById(id);
        return studentEntity.map(
                studentEntity1 -> {
                    StudentDTO dto = modelMapper.map(studentEntity1, StudentDTO.class);
                    dto.setProfessorCount(studentEntity1.getProfessors() == null ?
                            0 : studentEntity1.getProfessors().size());
                    dto.setSubjectCount(studentEntity1.getSubjects() == null ? 0 :
                            studentEntity1.getSubjects().size());
                    return dto;
                }

        );
    }

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {

        StudentEntity studentEntity = modelMapper.map(studentDTO, StudentEntity.class);
        StudentEntity saved = studentRepository.save(studentEntity);

        return modelMapper.map(saved, StudentDTO.class);
    }

    @Override
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        studentExistsById(id);
        return studentRepository.findById(id).map(student -> {
            student.setName(studentDTO.getName());

            StudentEntity updated = studentRepository.save(student);
            return modelMapper.map(updated, StudentDTO.class);
        }).orElseThrow(() -> new RuntimeException());
    }

    @Override
    public List<SubjectDTO> getSubjectsByStudentId(Long studentId) {
        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NoSuchElementException("Student not found with ID: " + studentId));

        return student.getSubjects().stream()
                .map(subject -> new SubjectDTO(subject.getId(), subject.getTitle()))
                .toList();
    }


    @Override
    public boolean deleteStudent(Long id) {
        studentExistsById(id);
        studentRepository.deleteById(id);
        return true;
    }
}
