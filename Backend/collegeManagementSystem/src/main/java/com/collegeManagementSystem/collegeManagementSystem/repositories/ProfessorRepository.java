package com.collegeManagementSystem.collegeManagementSystem.repositories;

import com.collegeManagementSystem.collegeManagementSystem.entities.ProfessorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<ProfessorEntity, Long> {
}
