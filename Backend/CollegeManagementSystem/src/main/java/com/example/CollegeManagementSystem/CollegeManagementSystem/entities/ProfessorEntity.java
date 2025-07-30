package com.example.CollegeManagementSystem.CollegeManagementSystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Data
@NoArgsConstructor
@Table(name = "professor")
public class ProfessorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ToString.Exclude
    @ManyToMany(mappedBy = "professors")
    private Set<StudentEntity> students = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "professor")
    private Set<SubjectEntity> subjects = new HashSet<>();
}
