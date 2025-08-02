package com.example.CollegeManagementSystem.CollegeManagementSystem;


import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.SubjectDTO;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.ProfessorEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.StudentEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.SubjectEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.AdmissionRecordRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.ProfessorRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.StudentRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.repositories.SubjectRepository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.services.SubjectServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLOutput;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
public class ProfessorTest {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private AdmissionRecordRepository admissionRecordRepository;

    @Autowired
    private SubjectServiceImpl subjectService;

    @Test
    public void testProfessor() {
        List<ProfessorEntity> professor = professorRepository.findAll();
        for (ProfessorEntity p : professor) {
            System.out.println(p);
        }
    }

    @Test
    public void testStudents() {
        List<StudentEntity> students = studentRepository.findAll();
        for (StudentEntity s : students) {
            System.out.println(s);
        }

    }

    @Test
    public void testCreateSubject() {
        // Fetch existing professors and students from DB
        List<ProfessorEntity> professors = professorRepository.findAll();
        List<StudentEntity> students = studentRepository.findAll();

        if (professors.isEmpty() || students.isEmpty()) {
            System.out.println("No professors or students found to create subject.");
            return;
        }

        ProfessorEntity professor = professors.get(0);

        Set<Long> studentIds = students.stream()
                .map(student -> student.getId())
                .collect(Collectors.toSet());

        // Prepare SubjectDTO
        SubjectDTO newSubject = new SubjectDTO();
        newSubject.setName("Test Subject");
        newSubject.setProfessorId(professor.getId());
        newSubject.setStudents(studentIds); // assuming your DTO has 'students' as Set<Long>

        // Call service method
        SubjectDTO createdSubject = subjectService.createSubject(newSubject);

        // Assertions / Prints
        System.out.println("Created Subject: " + createdSubject);
        System.out.println("Assigned Professor ID: " + createdSubject.getProfessorId());
        System.out.println("Assigned Professor Name: " + createdSubject.getProfessorName());
        System.out.println("Assigned Students: " + createdSubject.getStudents());
    }

    @Test
    void testGetSubjectById() {

        ProfessorEntity professor = new ProfessorEntity();
        professor.setName("Dr. Smith");
        professor = professorRepository.save(professor);

        // Create and save students
        StudentEntity student1 = new StudentEntity();
        student1.setName("Alice");
        student1 = studentRepository.save(student1);

        StudentEntity student2 = new StudentEntity();
        student2.setName("Bob");
        student2 = studentRepository.save(student2);

        SubjectEntity subject = new SubjectEntity();
        subject.setName("Math");
        subject.setProfessor(professor);

        Set<StudentEntity> students = new HashSet<>();
        students.add(student1);
        students.add(student2);
        subject.setStudents(students);

        subject = subjectRepository.save(subject);

        SubjectDTO result = subjectService.getSubjectById(subject.getId());
        System.out.println(result);

    }

    @Test
    void testGetAllSubjects() {

        List<SubjectDTO> subjects = subjectService.getAllSubjects();
        for (SubjectDTO s : subjects) {
            System.out.println(s);
        }
    }

    @Test
    @Transactional
    public void testUpdateSubject() {
        // Step 1: Create and save an initial professor
        ProfessorEntity originalProfessor = new ProfessorEntity();
        originalProfessor.setName("Dr. Original Professor");
        originalProfessor = professorRepository.save(originalProfessor);

        // Step 2: Create and save an initial subject linked to original professor
        SubjectEntity subject = new SubjectEntity();
        subject.setName("Original Subject");
        subject.setProfessor(originalProfessor);
        subject = subjectRepository.save(subject);

        // Step 3: Create and save a new professor for update
        ProfessorEntity newProfessor = new ProfessorEntity();
        newProfessor.setName("Dr. New Professor");
        newProfessor = professorRepository.save(newProfessor);

        // Step 4: Create and save some students
        StudentEntity student1 = new StudentEntity();
        student1.setName("Alice");
        student1 = studentRepository.save(student1);

        StudentEntity student2 = new StudentEntity();
        student2.setName("Bob");
        student2 = studentRepository.save(student2);

        Set<Long> updatedStudentIds = new HashSet<>();
        updatedStudentIds.add(student1.getId());
        updatedStudentIds.add(student2.getId());

        // Step 5: Prepare updated SubjectDTO
        SubjectDTO updatedSubjectDTO = new SubjectDTO();
        updatedSubjectDTO.setName("Updated Subject Name");
        updatedSubjectDTO.setProfessorId(newProfessor.getId());
        updatedSubjectDTO.setStudents(updatedStudentIds);

        // Step 6: Call the updateSubject service method
        SubjectDTO updatedSubject = subjectService.updateSubject(subject.getId(), updatedSubjectDTO);

        // Step 7: Print updated subject DTO
        System.out.println("Updated Subject DTO: " + updatedSubject);

    }

    @Test
    @Transactional
    public void testPatch() {


        //1. create a professor
        ProfessorEntity professor = new ProfessorEntity();
        professor.setName("Dr. Harry Porter");
        professor = professorRepository.save(professor);



        //2. create and save some students
        StudentEntity student1 = StudentEntity.builder().name("Hermoine").build();
        StudentEntity student2 = StudentEntity.builder().name("Harry").build();
        student1 = studentRepository.save(student1);
        student2 = studentRepository.save(student2);

        Set<StudentEntity> studentEntities = new HashSet<>();
        studentEntities.add(student1);
        studentEntities.add(student2);


        //3. create a subject
        SubjectEntity subject = new SubjectEntity();
        subject.setName("Old Name");
        subject.setProfessor(professor);
        subject.setStudents(studentEntities);
        subject = subjectRepository.save(subject);


        //3. prepare the subject DTO and patch
        SubjectDTO newSubjectDTO = new SubjectDTO();
        newSubjectDTO.setName("Data Structures and Algorithms");
        newSubjectDTO.setProfessorId(professor.getId());
        newSubjectDTO.setStudents(Set.of(student1.getId(), student2.getId()));

        //4. call service method to insert subject
        SubjectDTO savedSubjectDTO = subjectService.patchUpdateSubject(subject.getId(), newSubjectDTO);

        // Step 7: Print the saved subject
        System.out.println("Saved Subject: " + savedSubjectDTO);
    }

    @Test
    public void testDeleteSubjectById() {
        // Create and save a professor
        ProfessorEntity professor = new ProfessorEntity();
        professor.setName("Dr. Einstein");
        professor = professorRepository.save(professor);

        // Create and save students
        StudentEntity student1 = new StudentEntity();
        student1.setName("Student one");
        student1 = studentRepository.save(student1);

        StudentEntity student2 = new StudentEntity();
        student2.setName("Student two");
        student2 = studentRepository.save(student2);

        Set<StudentEntity> students = new HashSet<>();
        students.add(student1);
        students.add(student2);


        // Create and save a subject
        SubjectEntity subject = new SubjectEntity();
        subject.setName("Subject To Delete");
        subject.setProfessor(professor);
        subject.setStudents(students);
        subject = subjectRepository.save(subject);

        // Call the service method
        subjectService.deleteSubjectById(subject.getId());

        //Check and print whether it was deleted
        boolean isDeleted = !subjectRepository.existsById(subject.getId());
        System.out.println(isDeleted);
    }

    @Test
    @Transactional
    public void testAssignProfessorToSubejct() {
        //create and save professor
        ProfessorEntity professor = new ProfessorEntity();
        professor.setName("Dr. Assign");
        professor = professorRepository.save(professor);

        //create and save professor
        SubjectEntity subject = new SubjectEntity();
        subject.setName("Subject X");
        subject.setProfessor(professor);
        subject = subjectRepository.save(subject);

        //call the service method
        subjectService.assignProfessorToSubject(subject.getId(), professor.getId());

        //verify
        SubjectEntity updatedSubject = subjectRepository.findById(subject.getId()).orElseThrow();
        System.out.println(updatedSubject.getProfessor().getName());//should print "Dr. Assign"

    }

    @Test
    @Transactional
    public void testRemoveProfessorFromSubject() {
        //create a new professor
        ProfessorEntity professor = new ProfessorEntity();
        professor.setName("Dr. Removed");
        professor = professorRepository.save(professor);

        //create a new subject
        SubjectEntity subject = new SubjectEntity();
        subject.setProfessor(professor);
        subject.setProfessorRemoved(false);
        subject = subjectRepository.save(subject);


        //Actual removal logic
        subject.setProfessor(null);
        subject.setProfessorRemoved(true);
        subject = subjectRepository.save(subject);

        //call the method to remove professor
        SubjectEntity updatedSubject = subjectRepository.findById(subject.getId()).orElseThrow();

        //print the professorRemoved flag
        System.out.println("professorRemoved: " + updatedSubject.isProfessorRemoved());
    }


    @Test
    @Transactional
    public void testAssignStudentsToSubjects() {
        //1. fetch list of all students and subjects
        List<SubjectEntity> subjects = subjectRepository.findAll();
        List<StudentEntity> students = studentRepository.findAll();

        if (subjects.isEmpty() || students.isEmpty()) {
            System.out.println("No students or subjects found in DB");
        }

        //2. grab the 1st subject and create a Set of student IDs, limit them to 2
        SubjectEntity subject = subjects.get(0);

        Set<Long> studentIds = students.stream()
                .limit(2)
                .map(i -> i.getId())
                .collect(Collectors.toSet());


        //3. call the service method
        subjectService.assignStudentToSubject(subject.getId(), studentIds);

        // fetch the updated subject
        SubjectEntity updatedSubject = subjectRepository.findById(subject.getId()).orElseThrow();

        //Print verification
        System.out.println("Subject Name: " + updatedSubject.getName());
        System.out.println("Assigned Students: ");
        updatedSubject.getStudents().forEach(s -> System.out.println("_" + s.getName()));

    }

    @Test
    @Transactional
    public void testRemoveStudentsFromSubjects() {

        Long subjectId = 4L;

       Set<Long> studentIds = new HashSet<>(Arrays.asList(11l));

        subjectService.removeStudentFromSubject(subjectId, studentIds);
        System.out.println("Removed students with IDs: " + studentIds);

        SubjectEntity updatedSubject = subjectRepository.findById(subjectId).orElseThrow();
        System.out.println("Students related to subject: " + updatedSubject.getStudents());

    }



}









