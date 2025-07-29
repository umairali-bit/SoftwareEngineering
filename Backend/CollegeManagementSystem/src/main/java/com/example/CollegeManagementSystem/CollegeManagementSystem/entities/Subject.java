package com.example.CollegeManagementSystem.CollegeManagementSystem.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne //owning
    @JoinColumn(nullable = false)
    private Professor professor;

    private Set<Student> student = new HashSet<>();

    public Subject() {
    }

    public Subject(Long id, String title, Professor professor, Set<Student> student) {
        this.id = id;
        this.title = title;
        this.professor = professor;
        this.student = student;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Set<Student> getStudent() {
        return student;
    }

    public void setStudent(Set<Student> student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", professor=" + professor +
                ", student=" + student +
                '}';
    }
}
