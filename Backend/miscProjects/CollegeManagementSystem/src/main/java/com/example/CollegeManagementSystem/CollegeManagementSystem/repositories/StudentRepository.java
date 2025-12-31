package com.example.CollegeManagementSystem.CollegeManagementSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.StudentEntity;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {


    @Query("""
    select distinct s 
    from StudentEntity s
    left join fetch s.professors
    left join fetch s.subjects
    where s.id = :id
    """)
    Optional<StudentEntity> findWithProfessorsAndSubjectsById(@Param("id") Long id);



}
