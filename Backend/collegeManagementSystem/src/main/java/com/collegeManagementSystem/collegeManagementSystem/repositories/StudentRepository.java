package com.collegeManagementSystem.collegeManagementSystem.repositories;

import com.collegeManagementSystem.collegeManagementSystem.entities.StudentEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    @EntityGraph(attributePaths = {"professors", "subjects"})
    @Query("SELECT s FROM StudentEntity s")
    List<StudentEntity> findAllWithProfessorsAndSubjects();

    @EntityGraph(attributePaths = {"professors", "subjects"})
    Optional<StudentEntity> findById(Long id);  // Override not required, method matches
}

