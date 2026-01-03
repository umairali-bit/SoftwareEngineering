package com.example.testing.testingapp.entities;


import jakarta.persistence.*;
import org.modelmapper.internal.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.annotation.processing.Generated;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String name;

    private Long salary;

}
