package com.collegeManagementSystem.collegeManagementSystem.entities;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "subjects")
public class SubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToMany
    private ProfessorEntity professor;

    private List<StudentEntity> students;




}
