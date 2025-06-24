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

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private ProfessorEntity professor;

    @ManyToMany(mappedBy = "subjects")
    private List<StudentEntity> students;




}
