package com.example.cachingApp.entities;
import jakarta.persistence.*;


import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employees")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(unique = true)
    private String email;

    private Integer age;
    private String role;
    private LocalDate birthDate;
    private boolean isActive;
    private Long salary;

    @ManyToOne
    private DepartmentEntity department;




}
