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
    private Set<Subject> subjects = new HashSet<>();

    @ManyToMany(mappedBy = "professors")//inverse side
    private Set<Student> students = new HashSet<>();

    public Professor() {
    }

    public Professor(Long id, String title, Set<Subject> subjects, Set<Student> students) {
        this.id = id;
        this.title = title;
        this.subjects = subjects;
        this.students = students;
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

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", subjects=" + subjects +
                ", students=" + students +
                '}';
    }
}
