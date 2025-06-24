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
    public StudentDTO getStudentById(Long id) {
        studentExistsById(id);
        StudentEntity student = studentRepository.findById(id).get();
        return modelMapper.map(student,StudentDTO.class);
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
        studentExistsById(studentId);
        return studentRepository.findById(studentId)
                .map(student -> student.getSubjects()
                        .stream()
                        .map(subject -> new SubjectDTO(subject.getId(), subject.getTitle()))
                        .toList())
                .orElse(List.of());
    }

    @Override
    public List<ProfessorDTO> getProfessorBySubjectId(Long subjectId) {

        return studentRepository.findAll().stream()
                .flatMap(student -> student.getSubjects().stream()
                        .filter(subject -> subject.getId().equals(subjectId))
                        .map(subject -> subject.getProfessor()))
                .distinct()
                .map(professor -> modelMapper.map(professor, ProfessorDTO.class))
                .toList();

    }

    @Override
    public boolean deleteStudent(Long id) {
        studentExistsById(id);
        studentRepository.deleteById(id);
        return true;
    }
}
