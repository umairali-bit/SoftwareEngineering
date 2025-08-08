package com.example.CollegeManagementSystem.CollegeManagementSystem.services;


import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.StudentDTO;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.AdmissionRecordEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.ProfessorEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.StudentEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.SubjectEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.ProfessorRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.StudentRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.SubjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

        // 1. dto to entity
        StudentEntity student = new StudentEntity();
        student.setName(studentDTO.getName());


        //2. Handle AdmissionRecord if provided
        if (studentDTO.getAdmissionRecord() != null) {

            AdmissionRecordEntity admissionRecord = new AdmissionRecordEntity();
            admissionRecord.setAdmissionDate(studentDTO.getAdmissionRecord().getAdmissionDate());
            admissionRecord.setStudent(student);
            admissionRecord.setFees(studentDTO.getAdmissionRecord().getFees());
            student.setAdmissionRecord(admissionRecord);

        }

        //3. save student
        StudentEntity savedStudent = studentRepository.save(student);


        //4 convert back to dto
        return modelMapper.map(savedStudent, StudentDTO.class);

    }

    @Override
    public StudentDTO getStudentById(Long id) {
        //1. Find if the ID is present
        StudentEntity student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student with the" +
                "following id is not found" + id));

        return modelMapper.map(student, StudentDTO.class);
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        List<StudentEntity> studentEntities = studentRepository.findAll();

        return studentEntities
                .stream()
                .map(studentEntity -> modelMapper.map(studentEntity, StudentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        StudentEntity existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with the ID: " + id));

        // Update student fields
        existingStudent.setName(studentDTO.getName());

        // Handle AdmissionRecord
        if (studentDTO.getAdmissionRecord() != null) {
            AdmissionRecordEntity admissionRecord = new AdmissionRecordEntity();
            admissionRecord.setAdmissionDate(studentDTO.getAdmissionRecord().getAdmissionDate());
            admissionRecord.setStudent(existingStudent);
            admissionRecord.setFees(studentDTO.getAdmissionRecord().getFees());

            existingStudent.setAdmissionRecord(admissionRecord);//maintaining bidirectional
        } else {
            existingStudent.setAdmissionRecord(null);
        }

        // Handle subjectIds
        if (studentDTO.getSubjectIds() != null) {
            Set<SubjectEntity> subjectEntities = studentDTO.getSubjectIds().stream()
                    .map(subjectId -> subjectRepository.findById(subjectId)
                    .orElseThrow(() -> new RuntimeException("Subject not found with ID: " + subjectId)))
                    .collect(Collectors.toSet());

            existingStudent.setSubjects(subjectEntities);

            //maintaining bidirectional relationship
            for (SubjectEntity subject : subjectEntities) {
                subject.getStudents().add(existingStudent);
            }
        } else {
            existingStudent.setSubjects(null);
        }


        //Handle Professor IDs
        if (studentDTO.getProfessorIds() != null) {
            Set<ProfessorEntity> professorsEntity = studentDTO.getProfessorIds().stream()
                    .map(professorId -> professorRepository.findById(professorId)
                            .orElseThrow(()-> new RuntimeException("Professor not found with ID: " + professorId)))
                    .collect(Collectors.toSet());

            existingStudent.setProfessors(professorsEntity);

            //maintain bidirectional
            for (ProfessorEntity professor : professorsEntity) {
                professor.getStudents().add(existingStudent);
            }

        }  else {
            existingStudent.setProfessors(null);
        }

        StudentEntity savedStudent = studentRepository.save(existingStudent);



        return modelMapper.map(savedStudent, StudentDTO.class);
    }
}
