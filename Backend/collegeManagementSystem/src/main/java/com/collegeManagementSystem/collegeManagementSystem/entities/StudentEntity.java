package com.collegeManagementSystem.collegeManagementSystem.entities;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "students")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private List<ProfessorEntity> professor;
    private List<SubjectEntity> subjects;



}
