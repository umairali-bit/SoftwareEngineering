package com.example.CollegeManagementSystem.CollegeManagementSystem.repositories;

import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.ProfessorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<ProfessorEntity, Long> {

}
