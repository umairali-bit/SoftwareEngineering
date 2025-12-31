package com.example.CollegeManagementSystem.CollegeManagementSystem.services;

import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.StudentDTO;

import java.util.List;
import java.util.Set;

public interface StudentService {

    // Basic CRUD
    StudentDTO createStudent(StudentDTO studentDTO);

    StudentDTO getStudentById(Long id);

    List<StudentDTO> getAllStudents();

    StudentDTO updateStudent(Long id, StudentDTO studentDetails); // Full update (PUT)

   boolean deleteStudent(Long id);

//     Partial update (PATCH)
    StudentDTO patchStudent(Long id, StudentDTO studentDTO);

//   Managing relationships
   void assignProfessorToStudent(Long studentId, Long professorId, Long subjectId);

   void removeProfessorFromStudent(Long studentId, Long professorId);

 //   void assignSubjectsToStudent(Long studentId, Set<Long> subjectIds);

  //  void removeSubjectFromStudent(Long studentId, Set<Long> subjectIds);
// Admission record handling
 //   void assignAdmissionRecordToStudent(Long studentId, Long admissionRecordId);

 //   void removeAdmissionRecordFromStudent(Long studentId);
}
