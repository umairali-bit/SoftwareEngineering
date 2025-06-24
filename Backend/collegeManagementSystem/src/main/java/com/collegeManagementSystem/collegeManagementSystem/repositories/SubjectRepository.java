package com.collegeManagementSystem.collegeManagementSystem.repositories;

import com.collegeManagementSystem.collegeManagementSystem.entities.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {
}
