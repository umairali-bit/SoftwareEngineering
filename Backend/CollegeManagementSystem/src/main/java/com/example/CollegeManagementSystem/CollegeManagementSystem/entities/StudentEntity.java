package com.example.CollegeManagementSystem.CollegeManagementSystem.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "student_professor",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "professor_id")

    )
    private Set<ProfessorEntity> professors = new HashSet<>();

    @ManyToMany(mappedBy = "students", fetch = FetchType.EAGER)
    private Set<SubjectEntity> subjects = new HashSet<>();

    public StudentEntity() {}

    public StudentEntity(Long id, String name, Set<ProfessorEntity> professors, Set<SubjectEntity> subjects) {
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

    public Set<ProfessorEntity> getProfessors() {
        return professors;
    }

    public void setProfessors(Set<ProfessorEntity> professors) {
        this.professors = professors;
    }

    public Set<SubjectEntity> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<SubjectEntity> subjects) {
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", professors=" + professors +
                ", subjects=" + subjects +
                '}';
    }
}
