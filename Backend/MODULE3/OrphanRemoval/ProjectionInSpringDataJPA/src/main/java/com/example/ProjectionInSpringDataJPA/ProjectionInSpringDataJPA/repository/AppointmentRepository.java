package com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.repository;

import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
