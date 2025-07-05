package com.example.prod_ready_features.prod_ready_features.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "posts")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;


}
