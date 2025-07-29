package com.example.CollegeManagementSystem.CollegeManagementSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.StudentEntity;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

}
