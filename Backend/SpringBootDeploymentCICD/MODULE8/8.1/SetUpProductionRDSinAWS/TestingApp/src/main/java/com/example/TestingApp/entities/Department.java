package com.codingshuttle.TestingApp.entities;

import jakarta.persistence.*;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dptTitle;
}
