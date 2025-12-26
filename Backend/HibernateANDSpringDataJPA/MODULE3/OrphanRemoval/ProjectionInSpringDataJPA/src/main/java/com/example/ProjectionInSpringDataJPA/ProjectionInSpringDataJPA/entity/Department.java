package com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @OneToOne
    @JoinColumn(nullable = false)
    private Doctor headDoctor;


    @ManyToMany
    private Set<Doctor> doctor = new HashSet<>();
}
