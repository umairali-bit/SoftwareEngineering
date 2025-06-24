package com.collegeManagementSystem.collegeManagementSystem.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import javax.security.auth.Subject;
import java.util.List;


@Entity
@Table(name = "professor")
public class ProfessorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String title;
    private List<Subject> subjects;
    private List<Student> students;


}


