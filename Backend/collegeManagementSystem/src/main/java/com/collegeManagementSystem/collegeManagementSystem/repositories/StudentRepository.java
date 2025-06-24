package com.collegeManagementSystem.collegeManagementSystem.repositories;

import com.collegeManagementSystem.collegeManagementSystem.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
}
