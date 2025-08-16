package com.example.CollegeManagementSystem.CollegeManagementSystem;

import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.ProfessorDTO;
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

import java.util.Set;

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



}
