package com.example.CollegeManagementSystem.CollegeManagementSystem.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name = "professors")
public class ProfessorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @OneToMany(mappedBy = "professor")
    private Set<SubjectEntity> subjects = new HashSet<>();

    @ManyToMany(mappedBy = "professors")
    private Set<StudentEntity> students = new HashSet<>();

    public ProfessorEntity() {}

    public ProfessorEntity(Long id, String title, Set<SubjectEntity> subjects, Set<StudentEntity> students) {
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

    public Set<SubjectEntity> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<SubjectEntity> subjects) {
        this.subjects = subjects;
    }

    public Set<StudentEntity> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentEntity> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "ProfessorEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", subjects=" + subjects +
                ", students=" + students +
                '}';
    }
}
