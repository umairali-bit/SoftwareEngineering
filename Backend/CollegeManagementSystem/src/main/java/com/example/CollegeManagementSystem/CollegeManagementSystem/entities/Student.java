package com.example.CollegeManagementSystem.CollegeManagementSystem.entities;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    private Set<Professor> professor = new HashSet<>();

    private Set<Subject> subject = new HashSet<>();

    public Student() {
    }

    public Student(Long id, String name, Set<Professor> professor, Set<Subject> subject) {
        this.id = id;
        this.name = name;
        this.professor = professor;
        this.subject = subject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Professor> getProfessor() {
        return professor;
    }

    public void setProfessor(Set<Professor> professor) {
        this.professor = professor;
    }

    public Set<Subject> getSubject() {
        return subject;
    }

    public void setSubject(Set<Subject> subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", professor=" + professor +
                ", subject=" + subject +
                '}';
    }
}
