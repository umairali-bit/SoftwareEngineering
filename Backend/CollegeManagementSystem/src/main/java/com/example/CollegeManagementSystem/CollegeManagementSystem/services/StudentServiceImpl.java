package com.example.CollegeManagementSystem.CollegeManagementSystem.services;


import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.StudentDTO;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.StudentEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.ProfessorRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.StudentRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.SubjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    private final SubjectRepository subjectRepository;

    private final StudentRepository studentRepository;

    private final ProfessorRepository professorRepository;

    private final ModelMapper modelMapper;


    public StudentServiceImpl(SubjectRepository subjectRepository, StudentRepository studentRepository
            , ProfessorRepository professorRepository, ModelMapper modelMapper) {
        this.subjectRepository = subjectRepository;
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {

        // dto to entity - persistence
        StudentEntity student = modelMapper.map(studentDTO, StudentEntity.class);
        StudentEntity savedStudent = studentRepository.save(student);

        //entity to dto - persistence
        return modelMapper.map(savedStudent, StudentDTO.class);
    }
}
