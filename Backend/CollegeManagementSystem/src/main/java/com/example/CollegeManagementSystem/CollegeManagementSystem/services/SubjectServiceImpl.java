package com.example.CollegeManagementSystem.CollegeManagementSystem.services;


import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.StudentSummaryDTO;
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
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final ProfessorRepository professorRepository;

    private final StudentRepository studentRepository;

    private final SubjectRepository subjectRepository;

    private final ModelMapper modelMapper;

    public SubjectServiceImpl(ProfessorRepository professorRepository, StudentRepository studentRepository, SubjectRepository subjectRepository, ModelMapper modelMapper) {
        this.professorRepository = professorRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public SubjectDTO createSubject(SubjectDTO subjectDTO) {
        // --- Step 1: Create a new SubjectEntity from the DTO ---
        SubjectEntity subject = new SubjectEntity();
        subject.setName(subjectDTO.getName()); // Set subject name from DTO

        // --- Step 2: Attach the Professor (Persistent State) ---
        ProfessorEntity professor = professorRepository.findById(subjectDTO.getProfessorId())
                .orElseThrow(() -> new RuntimeException("Professor not found with ID: " + subjectDTO.getProfessorId()));
        subject.setProfessor(professor); // Link professor to subject
        professor.getSubjects().add(subject); // Maintain bidirectional relationship

        // --- Step 3: Attach Students (Persistent State) ---
        Set<StudentEntity> managedStudents = new HashSet<>();
        for (StudentSummaryDTO studentId : subjectDTO.getStudents()) {
            StudentEntity student = studentRepository.findById(studentId.getId())
                    .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));
            managedStudents.add(student); // Collect managed student entity
        }
        subject.setStudents(managedStudents); // Link students to subject

        // --- Step 4: Save the SubjectEntity ---
        SubjectEntity savedSubject = subjectRepository.save(subject); // Now subject is persistent

        // --- Step 5: Prepare and return the SubjectDTO response ---
        SubjectDTO savedDTO = new SubjectDTO();
        savedDTO.setId(savedSubject.getId()); // Set generated ID
        savedDTO.setName(savedSubject.getName()); // Set subject name
        savedDTO.setProfessorId(savedSubject.getProfessor().getId()); // Set professor ID
        savedDTO.setProfessorName(savedSubject.getProfessor().getName()); // Set professor name

        // Map students into StudentSummaryDTO list
        List<StudentSummaryDTO> studentSummaries = savedSubject.getStudents().stream()
                .map(s -> new StudentSummaryDTO(s.getId(), s.getName()))
                .collect(Collectors.toList());
        savedDTO.setStudents(studentSummaries);

        return savedDTO;
    }

    @Override
    @Transactional
    public SubjectDTO getSubjectById(Long subjectId) {
        SubjectEntity subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        SubjectDTO dto = new SubjectDTO();
        dto.setId(subject.getId());
        dto.setName(subject.getName());

        if (subject.getProfessor() != null) {
            dto.setProfessorId(subject.getProfessor().getId());
            dto.setProfessorName(subject.getProfessor().getName());
        }

        if (subject.getStudents() != null) {
            Set<Long> studentsIds = subject.getStudents().stream()
                    .map(s -> s.getId())
                    .collect(Collectors.toSet());
            // Convert to List<StudentSummaryDTO>
            List<StudentSummaryDTO> studentSummaries = subject.getStudents().stream()
                    .map(s -> new StudentSummaryDTO(s.getId(), s.getName()))
                    .collect(Collectors.toList());

            dto.setStudents(studentSummaries);
        }



        return dto;
    }

    @Override
    @Transactional
    public List<SubjectDTO> getAllSubjects() {
        // Load from DB — the returned entity is in the Managed (Persistent) state
        List<SubjectEntity> subjectEntities = subjectRepository.findAll();

        return subjectEntities
                .stream()
                .map(subjectEntity -> modelMapper.map(subjectEntity, SubjectDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SubjectDTO updateSubject(Long subjectId, SubjectDTO subjectDTO) {
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
                    .map(s-> studentRepository.findById(s.getId())
                            .orElseThrow(() -> new RuntimeException("Student not found with ID: " + s.getId())))
                    .collect(Collectors.toSet());

            subjectEntity.setStudents(students);
        }

        //5. Save updated entity and return DTO
        SubjectEntity updatedSubject = subjectRepository.save(subjectEntity);


        // 6. Map back to DTO
        SubjectDTO updatedDTO = new SubjectDTO();
        updatedDTO.setId(updatedSubject.getId());
        updatedDTO.setName(updatedSubject.getName());


        if (updatedSubject.getProfessor() != null) {
            updatedDTO.setProfessorId(updatedSubject.getProfessor().getId());
            updatedDTO.setProfessorName(updatedSubject.getProfessor().getName());
        }

        // 7. Convert students -> List<StudentSummaryDTO>
        List<StudentSummaryDTO> studentSummaries = updatedSubject.getStudents().stream()
                .map(s -> new StudentSummaryDTO(s.getId(), s.getName()))
                .collect(Collectors.toList());
        updatedDTO.setStudents(studentSummaries);

        return updatedDTO;
    }

    @Override
    @Transactional
    public SubjectDTO patchUpdateSubject(Long subjectId, SubjectDTO subjectDTO) {

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
                    .map(studentId -> studentRepository.findById(studentId.getId())
                            .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId.getId())))
                    .collect(Collectors.toSet());

            subjectEntity.setStudents(studentEntities);

        }

        //5. Save a return updated entity
        SubjectEntity updatedSubject = subjectRepository.save(subjectEntity);

        //6. Map back to DTO manually especially, professor, student
        SubjectDTO updatedDTO = new SubjectDTO();
        updatedDTO.setId(updatedSubject.getId());
        if (updatedSubject.getProfessor() != null) {
            updatedDTO.setProfessorId(updatedSubject.getProfessor().getId());
            updatedDTO.setProfessorName(updatedSubject.getProfessor().getName());
        }
        List<StudentSummaryDTO> studentSummaries = updatedSubject.getStudents().stream()
                .map(s -> new StudentSummaryDTO(s.getId(), s.getName()))
                .toList();


        return updatedDTO;

    }



    @Override
    public boolean deleteSubjectById(Long subjectId) {
        if (!subjectRepository.existsById(subjectId)) {
            throw new RuntimeException("Subject does not exist with ID: " + subjectId);
        }

        subjectRepository.deleteById(subjectId);
        return true;


    }

    @Override
    public void assignProfessorToSubject(Long subjectId, Long professorId) {

        //Fetch subject Entity
        SubjectEntity subject = subjectRepository.findById(subjectId)
                .orElseThrow(()-> new RuntimeException("Subject not found with ID: " + subjectId));

        //Fetch Professor Entity
        ProfessorEntity professor = professorRepository.findById(professorId)
                .orElseThrow(()-> new RuntimeException("Professor not found with ID: " + professorId));

        //Set professor
        subject.setProfessor(professor);

        //Save subject
        subjectRepository.save(subject);


    }

    @Override
    @Transactional
    public void removeProfessorFromSubject(Long subjectId) {

        //Fetch the subjectEntity
        SubjectEntity subject = subjectRepository.findById(subjectId)
                .orElseThrow(()-> new RuntimeException("Subject not found with ID: " + subjectId));

        //Fetch professor from the subject
        ProfessorEntity current = subject.getProfessor();
        if (current != null) {
            //maintain bidirectional relationship, if present
            if (current.getSubjects() != null) {
                current.getSubjects().remove(subject);
            }
            subject.setProfessor(null);
        }

        // In a @Transactional service, explicit save isn't required, but harmless:
        subjectRepository.save(subject);


    }

    @Transactional
    @Override
    public void assignStudentToSubject(Long subjectId, Set<Long> studentIds) {
        SubjectEntity subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found with ID: " + subjectId));

        // Load all students and validate missing IDs
        List<StudentEntity> found = studentRepository.findAllById(studentIds);
        if (found.size() != studentIds.size()) {
            // find which IDs are missing for a clearer error
            Set<Long> foundIds = found.stream().map(StudentEntity::getId).collect(java.util.stream.Collectors.toSet());
            Set<Long> missing = new java.util.HashSet<>(studentIds);
            missing.removeAll(foundIds);
            throw new RuntimeException("Student(s) not found with IDs: " + missing);
        }

        // Add (idempotent if equals/hashCode by id)
        for (StudentEntity s : found) {
            if (subject.getStudents().add(s)) {   // owning side
                s.getSubjects().add(subject);     // inverse side sync
            }
        }

        subjectRepository.save(subject); // owning side save is enough
    }
    @Override
    @Transactional
    public void removeStudentFromSubject(Long subjectId, Set<Long> studentIds) {

        SubjectEntity subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found with ID: " + subjectId));

        Set<StudentEntity> students = new HashSet<>(studentRepository.findAllById(studentIds));

        for (StudentEntity student : students) {
            subject.getStudents().remove(student);
            student.getSubjects().remove(subject);
        }

        subjectRepository.save(subject);



    }


}
