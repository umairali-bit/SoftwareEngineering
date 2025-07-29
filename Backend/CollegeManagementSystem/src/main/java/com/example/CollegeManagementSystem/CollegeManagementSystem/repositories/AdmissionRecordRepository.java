package com.example.CollegeManagementSystem.CollegeManagementSystem.repositories;

import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.AdmissionRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AdmissionRecordRepository extends JpaRepository <AdmissionRecordEntity, Long>{
}
