package com.example.CollegeManagementSystem.CollegeManagementSystem.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name = "subjects")
public class SubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ProfessorEntity professor;

    @ManyToMany
    @JoinTable(
            name = "subject_student",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<StudentEntity> students = new HashSet<>();

    public SubjectEntity() {}

    public SubjectEntity(Long id, String title, ProfessorEntity professor, Set<StudentEntity> students) {
        this.id = id;
        this.title = title;
        this.professor = professor;
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

    public ProfessorEntity getProfessor() {
        return professor;
    }

    public void setProfessor(ProfessorEntity professor) {
        this.professor = professor;
    }

    public Set<StudentEntity> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentEntity> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "SubjectEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", professor=" + professor +
                ", students=" + students +
                '}';
    }
}
