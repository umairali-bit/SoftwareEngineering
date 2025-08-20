package com.example.CollegeManagementSystem.CollegeManagementSystem;

import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.ProfessorDTO;
import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.StudentDTO;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.ProfessorEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.StudentEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.SubjectEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.AdmissionRecordRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.ProfessorRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.StudentRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.SubjectRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.services.ProfessorService;
import com.example.CollegeManagementSystem.CollegeManagementSystem.services.StudentService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
public class ProfessorTest {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ProfessorService professorService;


    @Autowired
    private ModelMapper modelMapper;


    @Test
    @Transactional
    @Commit
    void createNewProfessor() {

        //create a student
        StudentEntity student = new StudentEntity();
        student.setName("Ben");
        studentRepository.save(student);

        // create a dummy professor to link with subject (to satisfy nullable = false)
        ProfessorEntity dummyProfessor = new ProfessorEntity();
        dummyProfessor.setName("Temp Prof");
        professorRepository.save(dummyProfessor);

        //create a subject
        SubjectEntity subject = new SubjectEntity();
        subject.setName("Physics");
        subject.setProfessor(dummyProfessor); // required
        subjectRepository.save(subject);



        //create DTO
        ProfessorDTO dto = new ProfessorDTO();
        dto.setName("Prof. Smith");
        dto.setStudentIds(Set.of(student.getId()));
        dto.setSubjectIds(Set.of(subject.getId()));

        ProfessorDTO savedProfessor = professorService.createProfessor(dto);

        System.out.println("Professor created: " + savedProfessor.getName());
        System.out.println("Linked Students: " + savedProfessor.getStudentIds());
        System.out.println("Linked Subjects: " + savedProfessor.getSubjectIds());

    }


    @Test
    @Commit
    void testDeleteProfessor() {
        Long professorId = 3L;

        professorService.deleteProfessor(professorId);
    }





    @Test
    @Transactional
    void getProfessor() {
        List<ProfessorEntity> professors = professorRepository.findAll();

        System.out.println("=== Professors with Students & Subjects ===");
        for (ProfessorEntity prof : professors) {
            System.out.println("Professor ID: " + prof.getId() + ", Name: " + prof.getName());

            // Print subjects
            if (prof.getSubjects().isEmpty()) {
                System.out.println("   Subjects: none");
            } else {
                for (SubjectEntity subject : prof.getSubjects()) {
                    System.out.println("   Subject ID: " + subject.getId() + ", Name: " + subject.getName());
                }
            }

            // Print students
            if (prof.getStudents().isEmpty()) {
                System.out.println("   Students: none");
            } else {
                for (StudentEntity student : prof.getStudents()) {
                    System.out.println("   Student ID: " + student.getId() + ", Name: " + student.getName());
                }
            }
        }
    }


    @Test
    @Commit
    @Transactional
    void patchExistingProfessor() {
        // Arrange – fetch an existing professor
        ProfessorEntity existing = professorRepository.findAll().get(0);

        // Create a new student
        StudentEntity student = new StudentEntity();
        student.setName("Walter Jr");
        studentRepository.save(student);

        // Create a new subject and link it to professor
        SubjectEntity subject = new SubjectEntity();
        subject.setName("Machine Learning");
        subject.setProfessor(existing);  // bidirectional link
        subjectRepository.save(subject);

        // Build patch DTO
        ProfessorDTO patchDTO = new ProfessorDTO();
        patchDTO.setName(existing.getName()); // keep same name
        patchDTO.setStudentIds(Set.of(student.getId()));
        patchDTO.setSubjectIds(Set.of(subject.getId()));

        // Act – patch professor
        ProfessorDTO updated = professorService.patchProfessor(existing.getId(), patchDTO);

        // Assert – print results
        System.out.println("Professor updated: " + updated.getName());
        System.out.println("Linked Students: " + updated.getStudentIds());
        System.out.println("Linked Subjects: " + updated.getSubjectIds());

        // Fetch professor again to verify persistence
        ProfessorEntity refreshed = professorRepository.findById(existing.getId())
                .orElseThrow(() -> new RuntimeException("Professor not found"));

        System.out.println("Professors linked to student: " +
                refreshed.getStudents().stream().map(StudentEntity::getName).toList());

        System.out.println("Subjects linked to professor: " +
                refreshed.getSubjects().stream().map(SubjectEntity::getName).toList());
    }


    @Test
    @Transactional
    @Commit
    void  testAssigningSubjectToProfessor() {
        // Arrange – fetch an existing professor
        ProfessorEntity existing = professorRepository.findAll().get(2);

        SubjectEntity subject1 = new SubjectEntity();
        subject1.setName("Introduction To Linux");
        subject1.setProfessor(existing);  // bidirectional link
        subjectRepository.save(subject1);


        SubjectEntity subject2 = new SubjectEntity();
        subject2.setName("Linear Algebra");
        subject2.setProfessor(existing);  // bidirectional link
        subjectRepository.save(subject2);

        Set<Long> subjectIds = Set.of(subject1.getId(), subject2.getId());

        // Act
        professorService.assignSubjectToProfessor(existing.getId(), subjectIds);

        // Fetch updated professor
        ProfessorEntity updated = professorRepository.findById(existing.getId()).orElseThrow();

        // Print results
        System.out.println("Professor: " + updated.getName());
        System.out.println("Subjects assigned:");
        updated.getSubjects().forEach(s ->
                System.out.println(" - " + s.getName() + " (Professor: " + s.getProfessor().getName() + ")")
        );

    }


    @Test
    void testAssignSubjectToProfessorWithMissingSubject() {
        ProfessorEntity professor = new ProfessorEntity();
        professor.setName("Dr. Missing");
        professor = professorRepository.save(professor);

        Set<Long> subjectIds = Set.of(999L);

        try {
            professorService.assignSubjectToProfessor(professor.getId(), subjectIds);
        } catch (RuntimeException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }
    }


    @Test
    @Transactional
    @Commit
    void testRemoveExistingSubjectFromProfessor() {
        Long professorId = 2L;
        Long subjectIdToRemove = 4L;

        //Fetch before removal
        ProfessorEntity beforeProfessor = professorRepository.findById(professorId)
                .orElseThrow(() -> new RuntimeException("Professor not found with id " + professorId));

        System.out.println("Before");
        beforeProfessor.getSubjects()
                .forEach(subjectEntity -> System.out.println(subjectEntity));


        //remove subject
        professorService.removeSubjectFromProfessor(professorId,Set.of(subjectIdToRemove));

        //Fetch after removal
        ProfessorEntity afterProfessor = professorRepository.findById(professorId)
                .orElseThrow(() -> new RuntimeException("Professor not found with id " + professorId));

        System.out.println("After");
        afterProfessor.getSubjects()
                .forEach(s -> System.out.println("Professor: " + afterProfessor.getName()
                + " Subjects: " + s.getName()));

    }




}
