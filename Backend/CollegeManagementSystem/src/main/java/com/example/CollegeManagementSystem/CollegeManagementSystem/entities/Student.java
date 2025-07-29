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

    @ManyToMany
    @JoinTable(
            name = "student_professor",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "professor_id"))
    private Set<Professor> professors = new HashSet<>();

    @ManyToMany(mappedBy = "students")
    private Set<Subject> subjects = new HashSet<>();

    public Student() {
    }

    public Student(Long id, String name, Set<Professor> professors, Set<Subject> subjects) {
        this.id = id;
        this.name = name;
        this.professors = professors;
        this.subjects = subjects;
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

    public Set<Professor> getProfessors() {
        return professors;
    }

    public void setProfessor(Set<Professor> professors) {
        this.professors = professors;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubject(Set<Subject> subjects) {
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", professors=" + professors +
                ", subjects=" + subjects +
                '}';
    }
}
