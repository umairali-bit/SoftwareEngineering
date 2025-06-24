package com.collegeManagementSystem.collegeManagementSystem.entities;

import jakarta.persistence.*;
import lombok.*;

import javax.security.auth.Subject;
import java.util.List;


@Entity
@Table(name = "professor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String title;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    private List<SubjectEntity> subjects;

    @ManyToMany
    @JoinTable(
            name = "professor_student",
            joinColumns = @JoinColumn(name = "professor_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<StudentEntity> students;



}


