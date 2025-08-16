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

    private final ModelMapper modelMapper;


    public ProfessorServiceImpl(SubjectRepository subjectRepository, StudentRepository studentRepository,
                                ProfessorRepository professorRepository, ModelMapper modelMapper) {
        this.subjectRepository = subjectRepository;
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public ProfessorDTO createProfessor(ProfessorDTO professorDTO) {

        // 1. Map DTO to Entity (basic fields only)
        ProfessorEntity professor = modelMapper.map(professorDTO, ProfessorEntity.class);

        // 2. Handle students
        if (professorDTO.getStudentIds() != null && !professorDTO.getStudentIds().isEmpty()) {
            Set<StudentEntity> studentEntities = professorDTO.getStudentIds().stream()
                    .map(studentId -> studentRepository.findById(studentId)
                            .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId)))
                    .collect(Collectors.toSet());

            professor.setStudents(studentEntities);

            // Maintain bidirectional relationship
            for (StudentEntity student : studentEntities) {
                student.getProfessors().add(professor);
            }
        }

        // 3. Handle subjects
        if (professorDTO.getSubjectIds() != null && !professorDTO.getSubjectIds().isEmpty()) {
            Set<SubjectEntity> subjectEntities = professorDTO.getSubjectIds().stream()
                    .map(subjectId -> subjectRepository.findById(subjectId)
                            .orElseThrow(() -> new RuntimeException("Subject not found with ID: " + subjectId)))
                    .collect(Collectors.toSet());

            professor.setSubjects(subjectEntities);

            // Maintain bidirectional relationship
            for (SubjectEntity subject : subjectEntities) {
                subject.setProfessor(professor); // one-to-many case
                // OR subject.getProfessors().add(professor); // many-to-many case
            }
        }

        // 4. Save to DB
        ProfessorEntity savedProfessor = professorRepository.save(professor);

        // 5. Manually map back to DTO
        ProfessorDTO dto = modelMapper.map(savedProfessor, ProfessorDTO.class);
        dto.setStudentIds(savedProfessor.getStudents().stream()
                .map(StudentEntity::getId)
                .collect(Collectors.toSet()));
        dto.setSubjectIds(savedProfessor.getSubjects().stream()
                .map(SubjectEntity::getId)
                .collect(Collectors.toSet()));

        return dto;

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

    @Override
    public void deleteProfessor(Long id) {
        //Fetch the professor entity
        ProfessorEntity professor = professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor not found with the ID: " + id));
        //delete
        professorRepository.delete(professor); //map it to 404 not found
    }

    @Override
    public ProfessorDTO patchProfessor(Long id, ProfessorDTO professorDTO) {

        //find the professor
        ProfessorEntity existingProfessor = professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor not found with the ID: " + id));

        //start patching fields
        //name
        if (professorDTO.getName() != null) {
            existingProfessor.setName(professorDTO.getName());


        }

        //subject IDS
        if (professorDTO.getSubjectIds() != null) {
            Set<Long> subjectsIDs = professorDTO.getSubjectIds();

            Set<SubjectEntity> subjects = subjectsIDs.stream()
                    .map(subjectsID -> subjectRepository.findById(subjectsID)
                            .orElseThrow(() -> new RuntimeException("Subject not found with ID: " +subjectsID)))
                    .collect(Collectors.toSet());

            existingProfessor.setSubjects(subjects);
        }

        //studentIDs
        if (professorDTO.getStudentIds() != null) {
            Set<Long> studentsIDs = professorDTO.getStudentIds();

            Set<StudentEntity> students = studentsIDs.stream()
                    .map(studentID -> studentRepository.findById(studentID)
                            .orElseThrow(() -> new RuntimeException("Student not foudn with ID: " + studentID)))
                    .collect(Collectors.toSet());

            existingProfessor.setStudents(students);
        }


        //save the existing professor in ProfessorEntity
        ProfessorEntity savedProfessor = professorRepository.save(existingProfessor);

        //Entity
        ProfessorDTO dto = modelMapper.map(savedProfessor, ProfessorDTO.class);
        dto.setStudentIds(savedProfessor.getStudents().stream()
                .map(studentEntity -> studentEntity.getId()).collect(Collectors.toSet()));
        dto.setSubjectIds(savedProfessor.getSubjects().stream()
                .map(subjectEntity -> subjectEntity.getId()).collect(Collectors.toSet()));
        return dto;
    }


}























