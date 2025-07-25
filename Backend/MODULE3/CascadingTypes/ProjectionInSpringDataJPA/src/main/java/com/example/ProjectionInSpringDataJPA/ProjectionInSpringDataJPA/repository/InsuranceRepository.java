package com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.repository;

import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {


}
