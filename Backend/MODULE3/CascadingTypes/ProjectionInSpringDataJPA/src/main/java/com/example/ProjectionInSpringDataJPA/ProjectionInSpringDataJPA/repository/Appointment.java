package com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Appointment extends JpaRepository<Appointment, Long> {
}
