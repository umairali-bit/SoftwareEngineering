package com.collegeManagementSystem.collegeManagementSystem.repositories;

import com.collegeManagementSystem.collegeManagementSystem.entities.AdmissionRecordEntity;
import org.modelmapper.internal.bytebuddy.dynamic.DynamicType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdmissionRecordRepository extends JpaRepository<AdmissionRecordEntity, Long> {

    Optional<AdmissionRecordEntity> findByStudent_id(Long studentId);
}
