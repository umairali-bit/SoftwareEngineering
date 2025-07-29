package com.example.CollegeManagementSystem.CollegeManagementSystem.entities;


import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @OneToMany(mappedBy = "professor")//inverse side
    private Set<Subject> subject = new HashSet<>();

    private Set<Student> student = new HashSet<>();

    public Professor() {
    }

    public Professor(Long id, String title, Set<Subject> subject, Set<Student> student) {
        this.id = id;
        this.title = title;
        this.subject = subject;
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

    public Set<Subject> getSubject() {
        return subject;
    }

    public void setSubject(Set<Subject> subject) {
        this.subject = subject;
    }

    public Set<Student> getStudent() {
        return student;
    }

    public void setStudent(Set<Student> student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", subject=" + subject +
                ", student=" + student +
                '}';
    }
}
