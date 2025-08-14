package com.example.CollegeManagementSystem.CollegeManagementSystem;


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
import com.example.CollegeManagementSystem.CollegeManagementSystem.services.StudentService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringBootTest
public class StudentTest {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AdmissionRecordRepository admissionRecordRepository;

    @Autowired
    private StudentService studentService;


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    EntityManager em;


    @Test
    public void testCreateStudent_WithAdmissionRecord() {

        // prepare Admission record DTO
        AdmissionRecordDTO admissionRecord = AdmissionRecordDTO.builder()
                .admissionDate(LocalDateTime.of(2025, 8, 8, 0, 0))
                .fees(20000.00)
                .build();

        // prepare student DTO
        StudentDTO student = StudentDTO.builder()
                .name("Jesse Pinkman")
                .admissionRecord(admissionRecord)
                .build();


        // save to DB
        StudentDTO createdStudent = studentService.createStudent(student);


        // Print results
        System.out.println("Created Student ID: " + createdStudent.getId());
        System.out.println("Student Name: " + createdStudent.getName());
        System.out.println("Admission Date: " + createdStudent.getAdmissionRecord().getAdmissionDate());
        System.out.println("Admission Fees: " + createdStudent.getAdmissionRecord().getFees());

    }

    @Test
    @Commit
        //persists Ben in DB
    void testGetStudentById() {
        // create a student and its admission record

        AdmissionRecordEntity admissionRecordEntity = new AdmissionRecordEntity();
        admissionRecordEntity.setAdmissionDate(LocalDateTime.of(2025, 9, 9, 0, 0));
        admissionRecordEntity.setFees(30000.00);


        StudentEntity student = new StudentEntity();
        student.setName("Ben");
        student.setAdmissionRecord(admissionRecordEntity);
        admissionRecordEntity.setStudent(student); //bidirectional link

        // save to DB
        StudentEntity savedEntity = studentRepository.save(student);
        Long id = savedEntity.getId();

        //Act
        StudentDTO result = studentService.getStudentById(id);

        //Print
        System.out.println("Student ID: " + result.getId());
        System.out.println("Student Name: " + result.getName());
        System.out.println("Student Admission Record: " + result.getAdmissionRecord());

    }

    @Test
    void testGetAllStudents() {
        List<StudentDTO> studentDTOList = studentService.getAllStudents();
        System.out.println("Student List: " + studentDTOList);
    }

    @Test
    void testUpdateStudent() {
        //create AdmissionRecord, student, subject and professor

        ProfessorEntity professor = new ProfessorEntity();
        professor.setName("Mr. Smith");
        professorRepository.save(professor);

        SubjectEntity subject = new SubjectEntity();
        subject.setName("Calculus");
        subject.setProfessor(professor);
        subjectRepository.save(subject);

        StudentEntity student = new StudentEntity();
        student.setName("Original Name");
        student.setProfessors(Set.of(professor));
        student.setSubjects(Set.of(subject));
        studentRepository.save(student);

        AdmissionRecordEntity admissionRecord = new AdmissionRecordEntity();
        admissionRecord.setAdmissionDate(LocalDateTime.of(2025, 8, 8, 0, 0));
        admissionRecord.setFees(30000.00);
        admissionRecord.setStudent(student);

        admissionRecordRepository.save(admissionRecord);

        student.setAdmissionRecord(admissionRecord);
        studentRepository.save(student);


        // Prepare DTO
        StudentDTO updatedDTO = StudentDTO.builder()
                .name("Hank Schradar")
                .admissionRecord(AdmissionRecordDTO.builder()
                        .admissionDate(LocalDateTime.of(2025, 9, 9, 0, 0))
                        .fees(2500.00)
                        .build())
                .subjectIds(Set.of(subject.getId()))
                .professorIds(Set.of(professor.getId()))
                .build();


        // Call updatedStudent
        StudentDTO updatedStudent = studentService.updateStudent(student.getId(), updatedDTO);
        System.out.println("Updated Student ID: " + updatedStudent.getId());
        System.out.println("Student Name: " + updatedStudent.getName());
        System.out.println("Student Admission Record: " + updatedStudent.getAdmissionRecord());


    }

    @Test
    void deleteStudent() {
        StudentEntity student = new StudentEntity();
        student.setName("Test Student");
        student = studentRepository.save(student);


        Long studentId = student.getId();

        // Act - delete student
        studentService.deleteStudent(studentId);

        // Check if student still exists
        boolean exists = studentRepository.existsById(studentId);

        // Print the result
        if (exists) {
            System.out.println("Student was NOT deleted.");
        } else {
            System.out.println("Student deleted successfully.");
        }
    }


    @Test
    void patchExistingStudentsAdmissionRecords() {
        // Retrieve existing students by their IDs
        StudentEntity alice = studentRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Student 1 not found"));

        StudentEntity brian = studentRepository.findById(2L)
                .orElseThrow(() -> new RuntimeException("Student 2 not found"));

        StudentEntity chloe = studentRepository.findById(3L)
                .orElseThrow(() -> new RuntimeException("Student 3 not found"));

        // Create AdmissionRecordDTOs to patch
        AdmissionRecordDTO aliceAdmission = AdmissionRecordDTO.builder()
                .admissionDate(LocalDateTime.of(2025, 8, 1, 0, 0))
                .fees(15000.00)
                .build();

        AdmissionRecordDTO brianAdmission = AdmissionRecordDTO.builder()
                .admissionDate(LocalDateTime.of(2025, 8, 2, 0, 0))
                .fees(16000.00)
                .build();

        AdmissionRecordDTO chloeAdmission = AdmissionRecordDTO.builder()
                .admissionDate(LocalDateTime.of(2025, 8, 3, 0, 0))
                .fees(15500.00)
                .build();

        // Patch DTOs
        StudentDTO alicePatch = StudentDTO.builder()
                .admissionRecord(aliceAdmission)
                .build();

        StudentDTO brianPatch = StudentDTO.builder()
                .admissionRecord(brianAdmission)
                .build();

        StudentDTO chloePatch = StudentDTO.builder()
                .admissionRecord(chloeAdmission)
                .build();

        // Patch existing students by their IDs
        StudentDTO updatedAlice = studentService.patchStudent(alice.getId(), alicePatch);
        StudentDTO updatedBrian = studentService.patchStudent(brian.getId(), brianPatch);
        StudentDTO updatedChloe = studentService.patchStudent(chloe.getId(), chloePatch);

        // Print updated info
        System.out.println("Updated Alice: " + updatedAlice);
        System.out.println("Updated Brian: " + updatedBrian);
        System.out.println("Updated Chloe: " + updatedChloe);
    }


    @Test
    @Transactional
    void assignProfessorToStudent_shouldFail_whenStudentNotEnrolled() {
        // Professor who teaches Astrophysics
        ProfessorEntity prof = new ProfessorEntity();
        prof.setName("Gus Fring");
        prof = professorRepository.save(prof);

        SubjectEntity astro = new SubjectEntity();
        astro.setName("Astrophysics");
        astro.setProfessor(prof);           // required (nullable=false)
        prof.getSubjects().add(astro);
        astro = subjectRepository.save(astro);
        professorRepository.save(prof);

        // Student NOT enrolled in astro
        StudentEntity student = new StudentEntity();
        student.setName("Walter White Jr");
        student = studentRepository.save(student);

        System.out.println("CASE 1 — Expect failure: student NOT enrolled in subject");
        // Will throw "Student is not enrolled in the subject"
        studentService.assignProfessorToStudent(student.getId(), prof.getId(), astro.getId());
    }

    @Test
    @Transactional
    void assignProfessorToStudent_shouldFail_whenProfessorNotTeachingSubject() {
        // Professor A (the one we will pass to service)
        ProfessorEntity profA = new ProfessorEntity();
        profA.setName("Mike Ehrmantraut");
        profA = professorRepository.save(profA);

        // Professor B (actually teaches Chemistry)
        ProfessorEntity profB = new ProfessorEntity();
        profB.setName("Gale Boetticher");
        profB = professorRepository.save(profB);

        // Subject Chemistry is taught by profB (NOT profA)
        SubjectEntity chem = new SubjectEntity();
        chem.setName("Chemistry");
        chem.setProfessor(profB);          // satisfy DB constraint
        profB.getSubjects().add(chem);
        chem = subjectRepository.save(chem);
        professorRepository.save(profB);

        // Student IS enrolled in Chemistry
        StudentEntity student = new StudentEntity();
        student.setName("Jesse Pinkman");
        chem.getStudents().add(student);    // owning side for ManyToMany (if Subject owns)
        student.getSubjects().add(chem);    // inverse for in-memory consistency
        student = studentRepository.save(student);
        subjectRepository.save(chem);

        System.out.println("CASE 2 — Expect failure: professor DOES NOT teach subject");
        // Will throw "Professor does not teach the subject"
        studentService.assignProfessorToStudent(student.getId(), profA.getId(), chem.getId());
    }

    @Test
    @Transactional
    void assignProfessorToStudent_happyPath_printsState() {
        // Create Professor
        ProfessorEntity professor = new ProfessorEntity();
        professor.setName("Dr. Sagan");
        professor = professorRepository.save(professor);

        // Create Subject and link to Professor (ensure both sides if bidirectional)
        SubjectEntity subject = new SubjectEntity();
        subject.setName("Astrophysics");
        // If your mapping is ManyToOne on Subject -> Professor:
        subject.setProfessor(professor);
        // If Professor has Set<SubjectEntity> subjects:
        professor.getSubjects().add(subject);

        subject = subjectRepository.save(subject);
        professor = professorRepository.save(professor);

        // Create Student and enroll in Subject (maintain both sides)
        StudentEntity student = new StudentEntity();
        student.setName("Ellie Arroway");
        student.getSubjects().add(subject);
        // If Subject has Set<StudentEntity> students:
        subject.getStudents().add(student);

        student = studentRepository.save(student);
        subject = subjectRepository.save(subject);

        System.out.println("Before assignment:");
        System.out.println("Student: " + student.getName() + " | subjects=" +
                student.getSubjects().stream().map(SubjectEntity::getName).toList());
        System.out.println("Professor: " + professor.getName() + " | teaches=" +
                professor.getSubjects().stream().map(SubjectEntity::getName).toList());

        // Call the service
        studentService.assignProfessorToStudent(student.getId(), professor.getId(), subject.getId());

        // Re-fetch with professors loaded (your repo method)
        StudentEntity updatedStudent =
                studentRepository.findWithProfessorsAndSubjectsById(student.getId()).orElseThrow();


        System.out.println("\nAfter assignment:");
        System.out.println("Student: " + updatedStudent.getName());
        System.out.println("Professors now linked to student: " +
                updatedStudent.getProfessors().stream().map(ProfessorEntity::getName).collect(Collectors.toSet()));
    }

    @Test
    @Transactional
    void removeProfessorFromStudent_andRemoveAstrophysics() {
        // 1️⃣ Create Professor
        ProfessorEntity professor = new ProfessorEntity();
        professor.setName("Gus Fring");
        professor = professorRepository.save(professor);

        // 2️⃣ Create Subject (Astrophysics) and link to Professor
        SubjectEntity subject = new SubjectEntity();
        subject.setName("Astrophysics");
        subject.setProfessor(professor);
        professor.getSubjects().add(subject); // bidirectional
        subject = subjectRepository.save(subject);

        // 3️⃣ Create Student and enroll in Subject
        StudentEntity student = new StudentEntity();
        student.setName("Walter White Jr");
        student.getSubjects().add(subject); // inverse
        subject.getStudents().add(student); // owning
        student.getProfessors().add(professor);
        professor.getStudents().add(student);
        student = studentRepository.save(student);

//        em.flush();
//        em.clear();

        System.out.println("Before removal:");
        StudentEntity beforeRemoval = studentRepository
                .findWithProfessorsAndSubjectsById(student.getId())
                .orElseThrow();
        System.out.println("Student: " + beforeRemoval.getName()
                + " | Professors=" + beforeRemoval.getProfessors().stream()
                .map(ProfessorEntity::getName).toList()
                + " | Subjects=" + beforeRemoval.getSubjects().stream()
                .map(SubjectEntity::getName).toList());

        // 4️⃣ Remove professor
        studentService.removeProfessorFromStudent(student.getId(), professor.getId());

        // 5️⃣ Remove "Astrophysics" subject from student (owning side)
        StudentEntity managedStudent = studentRepository.findById(student.getId())
                .orElseThrow();
        SubjectEntity managedSubject = subjectRepository.findById(subject.getId())
                .orElseThrow();
        managedSubject.getStudents().remove(managedStudent);
        managedStudent.getSubjects().remove(managedSubject);

        studentRepository.save(managedStudent);
        em.flush();
        em.clear();

        // 6️⃣ Verify after removal
        System.out.println("\nAfter removal:");
        StudentEntity afterRemoval = studentRepository
                .findWithProfessorsAndSubjectsById(student.getId())
                .orElseThrow();
        System.out.println("Student: " + afterRemoval.getName()
                + " | Professors=" + afterRemoval.getProfessors().stream()
                .map(ProfessorEntity::getName).toList()
                + " | Subjects=" + afterRemoval.getSubjects().stream()
                .map(SubjectEntity::getName).toList());
    }

    @Test
    @Transactional
    void demoRemoveProfessor() {
        // fetch for printing
        StudentEntity before = studentRepository.findWithProfessorsAndSubjectsById(6L).orElseThrow();

        studentService.removeProfessorFromStudent(6L, 5L);

        // re-fetch to see DB state
        StudentEntity after = studentRepository.findWithProfessorsAndSubjectsById(6L).orElseThrow();
        System.out.println("After: " + after.getName() + " -> " +
                after.getProfessors().stream().map(ProfessorEntity::getName).toList());
    }


    @Test
    @Transactional
    @Commit
    void assignSubjectsToStudent() {

        StudentEntity student = studentRepository.findById(2L).orElseThrow();

        Set<Long> subjectIds = Set.of(1L, 2L);

        studentService.assignSubjectsToStudent(student.getId(), subjectIds);

        StudentEntity updatedStudent = studentRepository.findById(student.getId())
                .orElseThrow();

        System.out.println("Student: " + updatedStudent.getName());
        System.out.println("Subjects assigned:");
        updatedStudent.getSubjects().forEach(sub ->
                System.out.println(" - " + sub.getName())
        );


    }


    @Test
    @Transactional
    @Commit
    void removeTestSubjectFromStudent() {
        Long studentId = 2L;
        Set<Long> subjectIds = Set.of(2L);

        // BEFORE
        StudentEntity before = studentRepository.findById(studentId).orElseThrow();
        System.out.println("Before: " + before.getName());
        before.getSubjects().forEach(s -> System.out.println("  HAS: " + s.getName()));

        // ACT
        studentService.removeSubjectFromStudent(studentId, subjectIds);

        // Force SQL and detach cache
        em.flush();
        em.clear();

        // AFTER
        StudentEntity after = studentRepository.findById(studentId).orElseThrow();
        System.out.println("After: " + after.getName());
        after.getSubjects().forEach(s -> System.out.println("  HAS: " + s.getName()));
    }


    @Test
    @Transactional
    @Commit
    void testAssignAdmissionRecordToExistingStudent() {
        // --- Fetch students before assignment ---
        StudentEntity student = studentRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Student 1 not found"));
        StudentEntity student2 = studentRepository.findById(2L)
                .orElseThrow(() -> new RuntimeException("Student 2 not found"));
        StudentEntity student3 = studentRepository.findById(3L)
                .orElseThrow(() -> new RuntimeException("Student 3 not found"));

        System.out.println("=== BEFORE ASSIGNMENT ===");
        System.out.println("Student 1: id=" + student.getId() + ", name=" + student.getName()
                + ", record=" + (student.getAdmissionRecord() == null ? "null" : student.getAdmissionRecord().getId()));
        System.out.println("Student 2: id=" + student2.getId() + ", name=" + student2.getName()
                + ", record=" + (student2.getAdmissionRecord() == null ? "null" : student2.getAdmissionRecord().getId()));
        System.out.println("Student 3: id=" + student3.getId() + ", name=" + student3.getName()
                + ", record=" + (student3.getAdmissionRecord() == null ? "null" : student3.getAdmissionRecord().getId()));

        // --- Create admission records ---
        AdmissionRecordEntity record = admissionRecordRepository.save(
                AdmissionRecordEntity.builder()
                        .fees(1800.00)
                        .admissionDate(LocalDateTime.of(2025, 8, 20, 10, 0))
                        .build()
        );

        AdmissionRecordEntity record2 = admissionRecordRepository.save(
                AdmissionRecordEntity.builder()
                        .fees(20000.00)
                        .admissionDate(LocalDateTime.of(2024, 7, 10, 10, 0))
                        .build()
        );

        AdmissionRecordEntity record3 = admissionRecordRepository.save(
                AdmissionRecordEntity.builder()
                        .fees(15000.00)
                        .admissionDate(LocalDateTime.of(2024, 2, 14, 0, 0))
                        .build()
        );

        System.out.println("\n=== CREATED NEW RECORDS ===");
        System.out.println("Record 1: id=" + record.getId() + ", fees=" + record.getFees() +
                ", date=" + record.getAdmissionDate());
        System.out.println("Record 2: id=" + record2.getId() + ", fees=" + record2.getFees() +
                ", date=" + record2.getAdmissionDate());
        System.out.println("Record 3: id=" + record3.getId() + ", fees=" + record3.getFees() +
                ", date=" + record3.getAdmissionDate());

        // --- Assign records ---
        studentService.assignAdmissionRecordToStudent(student.getId(), record.getId());
        studentService.assignAdmissionRecordToStudent(student2.getId(), record2.getId());
        studentService.assignAdmissionRecordToStudent(student3.getId(), record3.getId());

        // --- Fetch updated students and records ---
        StudentEntity updatedStudent = studentRepository.findById(student.getId())
                .orElseThrow(() -> new RuntimeException("Student 1 not found after assignment"));
        StudentEntity updatedStudent2 = studentRepository.findById(student2.getId())
                .orElseThrow(() -> new RuntimeException("Student 2 not found after assignment"));
        StudentEntity updatedStudent3 = studentRepository.findById(student3.getId())
                .orElseThrow(() -> new RuntimeException("Student 3 not found after assignment"));

        AdmissionRecordEntity updatedRecord = admissionRecordRepository.findById(record.getId())
                .orElseThrow(() -> new RuntimeException("Record 1 not found after assignment"));
        AdmissionRecordEntity updatedRecord2 = admissionRecordRepository.findById(record2.getId())
                .orElseThrow(() -> new RuntimeException("Record 2 not found after assignment"));
        AdmissionRecordEntity updatedRecord3 = admissionRecordRepository.findById(record3.getId())
                .orElseThrow(() -> new RuntimeException("Record 3 not found after assignment"));

        // --- Print after state ---
        System.out.println("\n=== AFTER ASSIGNMENT ===");
        System.out.println("Student 1: id=" + updatedStudent.getId() + ", name=" + updatedStudent.getName()
                + ", recordId=" + updatedStudent.getAdmissionRecord().getId());
        System.out.println("Record 1: id=" + updatedRecord.getId()
                + ", studentId=" + updatedRecord.getStudent().getId());

        System.out.println("Student 2: id=" + updatedStudent2.getId() + ", name=" + updatedStudent2.getName()
                + ", recordId=" + updatedStudent2.getAdmissionRecord().getId());
        System.out.println("Record 2: id=" + updatedRecord2.getId()
                + ", studentId=" + updatedRecord2.getStudent().getId());

        System.out.println("Student 3: id=" + updatedStudent3.getId() + ", name=" + updatedStudent3.getName()
                + ", recordId=" + updatedStudent3.getAdmissionRecord().getId());
        System.out.println("Record 3: id=" + updatedRecord3.getId()
                + ", studentId=" + updatedRecord3.getStudent().getId());
    }

}


























