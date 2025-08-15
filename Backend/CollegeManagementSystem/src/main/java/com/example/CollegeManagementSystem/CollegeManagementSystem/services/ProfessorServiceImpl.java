package com.example.CollegeManagementSystem.CollegeManagementSystem.services;

import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.ProfessorDTO;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.ProfessorEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.StudentEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.SubjectEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.AdmissionRecordRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.ProfessorRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.StudentRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.SubjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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


    @Override
    public ProfessorDTO createProfessor(ProfessorDTO professorDTO) {

        //1. Map DTO to entity
        ProfessorEntity professor = modelMapper.map(professorDTO, ProfessorEntity.class);

        //2. Save entity to DB
        ProfessorEntity savedProfessor = professorRepository.save(professor);

        //3. Map saved entity back to DTO
        return modelMapper.map(savedProfessor, ProfessorDTO.class);
    }

    @Override
    public ProfessorDTO getProfessorById(Long id) {

        // find if te ID is present
        ProfessorEntity professor = professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor not found with the ID: " + id));

        return modelMapper.map(professor, ProfessorDTO.class);

    }

    @Override
    public List<ProfessorDTO> getAllProfessors() {
        List<ProfessorEntity> professorEntities = professorRepository.findAll();

        return professorEntities
                .stream()
                .map(professorEntity -> modelMapper.map(professorEntity, ProfessorDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProfessorDTO updateProfessor(Long id, ProfessorDTO professorDTO) {

        //1. fetch the professor
        ProfessorEntity professor = professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor not found with the ID: " + id));

        //Update the professor fields
        professor.setName(professorDTO.getName());

        //Handle students
        if (professorDTO.getStudentIds() != null) {
            Set<StudentEntity> studentEntities = professorDTO.getStudentIds().stream()
                    .map(studentId -> studentRepository.findById(studentId)
                            .orElseThrow(() -> new RuntimeException("Student not found with the ID: " + studentId)))
                    .collect(Collectors.toSet());

            professor.setStudents(studentEntities);

            //maintaining bidirectional
            for (StudentEntity student : studentEntities) {
                student.getProfessors().add(professor);

            }

        } else {
            professor.setStudents(null);
        }



        //Handle Subjects
        if (professorDTO.getSubjectIds() != null) {
            Set<SubjectEntity> subjectEntities = professorDTO.getSubjectIds().stream()
                    .map(subjectId -> subjectRepository.findById(subjectId)
                            .orElseThrow(() -> new RuntimeException("Subject not found with ID: " + subjectId)))
                    .collect(Collectors.toSet());

            professor.setSubjects(subjectEntities);


            for (SubjectEntity subject : subjectEntities) {
                subject.setProfessor(professor);
            }

        } else {
            professor.setSubjects(null);
        }


        //save professor
        ProfessorEntity savedProfessor = professorRepository.save(professor);


        //entity to dto
        return modelMapper.map(savedProfessor, ProfessorDTO.class);

    }


}
