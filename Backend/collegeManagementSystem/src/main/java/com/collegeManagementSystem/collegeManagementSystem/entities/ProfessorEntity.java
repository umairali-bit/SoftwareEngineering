package com.collegeManagementSystem.collegeManagementSystem.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;




@Entity
@Table(name = "professor")
public class ProfessorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Example additional fields
    private String name;
    private String department;

    // Getters and Setters (optional if using Lombok)
}


