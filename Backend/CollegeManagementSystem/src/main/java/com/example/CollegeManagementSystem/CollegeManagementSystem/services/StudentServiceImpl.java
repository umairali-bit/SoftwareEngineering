package com.example.CollegeManagementSystem.CollegeManagementSystem.services;


import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.AdmissionRecordDTO;
import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.StudentDTO;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.AdmissionRecordEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.ProfessorEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.StudentEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.SubjectEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.AdmissionRecordRepository;
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
public class StudentServiceImpl implements StudentService {

    private final SubjectRepository subjectRepository;

    private final StudentRepository studentRepository;

    private final ProfessorRepository professorRepository;

    private final AdmissionRecordRepository admissionRecordRepository;

    private final ModelMapper modelMapper;


    public StudentServiceImpl(SubjectRepository subjectRepository, StudentRepository studentRepository
            , ProfessorRepository professorRepository, AdmissionRecordRepository admissionRecordRepository, ModelMapper modelMapper) {
        this.subjectRepository = subjectRepository;
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
        this.admissionRecordRepository = admissionRecordRepository;
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

    @Transactional
    @Override
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        StudentEntity existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with the ID: " + id));

        // Update student fields
        existingStudent.setName(studentDTO.getName());

        // Handle AdmissionRecord
        if (studentDTO.getAdmissionRecord() != null) {
            AdmissionRecordEntity admissionRecord = existingStudent.getAdmissionRecord();

            if (admissionRecord == null) {
                admissionRecord = new AdmissionRecordEntity();
                admissionRecord.setStudent(existingStudent);
            }
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
                            .orElseThrow(() -> new RuntimeException("Professor not found with ID: " + professorId)))
                    .collect(Collectors.toSet());

            existingStudent.setProfessors(professorsEntity);

            //maintain bidirectional
            for (ProfessorEntity professor : professorsEntity) {
                professor.getStudents().add(existingStudent);
            }

        } else {
            existingStudent.setProfessors(null);
        }

        StudentEntity savedStudent = studentRepository.save(existingStudent);


        return modelMapper.map(savedStudent, StudentDTO.class);
    }

    @Override
    public void deleteStudent(Long id) {

        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found: " + id));

        AdmissionRecordEntity record = student.getAdmissionRecord();
        if (record != null) {
            record.setStudent(null);
            student.setAdmissionRecord(null);
            admissionRecordRepository.save(record);
            // optionally: admissionRecordRepository.delete(record);
        }

        studentRepository.delete(student);
    }


    @Transactional
    @Override
    public StudentDTO patchStudent(Long id, StudentDTO studentDTO) {

        //1. find the id
        StudentEntity existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with the ID: " + id));

        // 2. patching name
        if (studentDTO.getName() != null) {
            existingStudent.setName(studentDTO.getName());
        }

        //3. patching AdmissionRecord
        if (studentDTO.getAdmissionRecord() != null) {
            AdmissionRecordDTO admissionRecordDTO = studentDTO.getAdmissionRecord();

            AdmissionRecordEntity admissionRecord = existingStudent.getAdmissionRecord();

            // if no admission record was associated with the student
            if (admissionRecord == null) {
                admissionRecord = new AdmissionRecordEntity();
                admissionRecord.setStudent(existingStudent);
                existingStudent.setAdmissionRecord(admissionRecord); // maintain bidirectional
            }

            // Now patch fields (common to both new or existing)
            if (admissionRecordDTO.getAdmissionDate() != null) {
                admissionRecord.setAdmissionDate(admissionRecordDTO.getAdmissionDate());
            }

            if (admissionRecordDTO.getFees() != null) {
                admissionRecord.setFees(admissionRecordDTO.getFees());
            }

        }

        //4. update subjects
        if (studentDTO.getSubjectIds() != null) {
            Set<Long> subjectIDs = studentDTO.getSubjectIds();

            Set<SubjectEntity> subjects = subjectIDs.stream()
                    .map(subjectID -> subjectRepository.findById(subjectID)
                            .orElseThrow(() -> new RuntimeException("Subject not found with ID: " + subjectID)))
                    .collect(Collectors.toSet());

            existingStudent.setSubjects(subjects);
        }

        //5. update professors
        if (studentDTO.getProfessorIds() != null) {
            Set<Long> professorIDs = studentDTO.getProfessorIds();

            Set<ProfessorEntity> professors = professorIDs.stream()
                    .map(professorID -> professorRepository.findById(professorID)
                            .orElseThrow(() -> new RuntimeException("Professor not found with ID: " + professorID)))
                    .collect(Collectors.toSet());

            existingStudent.setProfessors(professors);
        }

        //6. save the existing student in the StudentEntity
        StudentEntity savedStudent = studentRepository.save(existingStudent);

        return modelMapper.map(savedStudent, StudentDTO.class);
    }

    @Override
    @Transactional
    public void assignProfessorToStudent(Long studentId, Long professorId, Long subjectId) {

        //Fetch Student Entity
        StudentEntity student = studentRepository.findWithProfessorsAndSubjectsById(studentId)
                .orElseThrow(() -> new RuntimeException("Student is not found with ID: " + studentId));

        //Fetch Professor Entity
        ProfessorEntity professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new RuntimeException("Professor is not found with ID: " + professorId));

        //Fetch Subject Entity
        SubjectEntity subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject is not found with ID: " + subjectId));

        //Validation: student must be enrolled in the subject
        if (!student.getSubjects().contains(subject)) {
            throw new RuntimeException("Student is not enrolled in the subject");
        }

        //Validation: professor must be teaching the subject
        if (!professor.getSubjects().contains(subject)) {
            throw new RuntimeException("Professor does not teach the subject");
        }

        //Validation if professor is already assigned to this student
        if (student.getProfessors().contains(professor)) {
            throw new IllegalArgumentException("Professor is already assigned to this student");
        }

        // Remove any existing professor for this subject
        student.getProfessors().removeIf(
                existingProfessor -> existingProfessor.getSubjects().contains(subject)

        );

        //Set professor to Student
        student.getProfessors().add(professor);


        //Save entity
        studentRepository.save(student);


    }

    @Override
    @Transactional
    public void removeProfessorFromStudent(Long studentId, Long professorId) {

        //Fetch the Student
        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student is not found with ID: " + studentId));

        //Fetch Professor Entity
        ProfessorEntity professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new RuntimeException("Professor is not found with ID: " + professorId));

        //remove association from both sides
        student.getProfessors().remove(professor);
        professor.getStudents().remove(student);


        //persists change
        studentRepository.save(student);

    }

    @Override
    public void assignSubjectsToStudent(Long studentId, Set<Long> subjectIds) {

        //Fetch subjects - this method will hit the DB multiple times, look into assignStudentToSubjecty (Better!)
        Set<SubjectEntity> subjects = subjectIds.stream()
                .map(subjectID -> subjectRepository.findById(subjectID)
                        .orElseThrow(() -> new RuntimeException("Subject not found with ID: " + subjectID)))
                .collect(Collectors.toSet());

        //Fetch the student
        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));


        for (SubjectEntity s : subjects) {
            if (s.getStudents().add(student)) {// owning side
                student.getSubjects().add(s); // inverse sync
            }

            studentRepository.save(student);

        }


    }

    @Override
    @Transactional
    public void removeSubjectFromStudent(Long studentId, Set<Long> subjectIds) {

        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));

        List<SubjectEntity> found = subjectRepository.findAllById(subjectIds);
        Set<Long> foundIds = found.stream().map(subjectEntity -> subjectEntity.getId())
                .collect(java.util.stream.Collectors.toSet());
        if (found.size() != subjectIds.size()) {

            // find which IDS are missing for a clearer error
            Set<Long> missing = new HashSet<>(subjectIds);
            missing.removeAll(foundIds);
            throw new RuntimeException("Subject(s) not found with IDs: " + missing);
        }

        // Remove only those that are actually linked
        Set<Long> notLinked = new HashSet<>(foundIds);
        for (SubjectEntity subject : found) {
            if ( subject.getStudents().remove(student)) {
                student.getSubjects().remove(subject);// keep inverse consistent
                notLinked.remove(subject.getId());
            }
        }

        subjectRepository.saveAll(found);
        studentRepository.save(student);


    }

    @Override
    public void assignAdmissionRecordToStudent(Long studentId, Long admissionRecordId) {

        //Fetch the student

        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));

        //Fetch the admissionRecord
        AdmissionRecordEntity admissionRecord = admissionRecordRepository.findById(admissionRecordId)
                .orElseThrow(() -> new RuntimeException("Admission record not found: " + admissionRecordId));


        // Prevent assigning a record that already belongs to a different student
        StudentEntity currentOwner = admissionRecord.getStudent();
        if (currentOwner != null && !currentOwner.getId().equals(student.getId())) {
            throw new IllegalStateException(
                    "Admission record " + admissionRecordId + " already assigned to a student " +currentOwner.getId());
        }

        //If student already has different admissionRecord, unlink it first
        AdmissionRecordEntity old = student.getAdmissionRecord();

        if (old != null && !old.getId().equals(admissionRecord.getId())) {
            old.setStudent(null);
            student.setAdmissionRecord(null);
        }


        //Link both sides
        student.setAdmissionRecord(admissionRecord); //owning side
        admissionRecord.setStudent(student);//inverse side

        //save the owning side
        studentRepository.save(student);
        admissionRecordRepository.save(admissionRecord);



    }

    @Transactional
    @Override
    public void removeAdmissionRecordFromStudent(Long studentId) {

        //Fetching the student
        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found: " + studentId));

        //Fetching the AdmissionRecord of the student
        AdmissionRecordEntity record = student.getAdmissionRecord();

        if (record == null) return;

        //unlink both sides
        record.setStudent(null);
        student.setAdmissionRecord(null);

        admissionRecordRepository.delete(record);


     }
}





























