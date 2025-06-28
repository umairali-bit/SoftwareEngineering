package com.libraryManagementSystem.libraryManagementSystem.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "Authors")
public class AuthorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "authors", cascade = CascadeType.ALL)
    private List<BookEntity> books = new ArrayList<>();







}
