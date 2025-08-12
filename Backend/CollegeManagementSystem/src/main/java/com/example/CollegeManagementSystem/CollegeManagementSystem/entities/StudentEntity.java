package com.example.CollegeManagementSystem.CollegeManagementSystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "student")
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "admission_record_id") // FK column in Student table
    private AdmissionRecordEntity admissionRecord;

    @ManyToMany
    @JoinTable(
            name = "student_professor",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "professor_id")
    )
    @ToString.Exclude
    private Set<ProfessorEntity> professors = new HashSet<>();


    @ManyToMany(mappedBy = "students")
    private Set<SubjectEntity> subjects = new HashSet<>();

    private boolean professorRemoved;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubjectEntity)) return false;
        SubjectEntity other = (SubjectEntity) o;
        return id != null && id.equals(other.getId());
    }
    @Override
    public int hashCode() { return 31; } // stable




}
