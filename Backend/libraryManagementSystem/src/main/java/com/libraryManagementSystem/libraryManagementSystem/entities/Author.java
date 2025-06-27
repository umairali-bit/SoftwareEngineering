package com.libraryManagementSystem.libraryManagementSystem.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Entity
@Data
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private Book book;







}
