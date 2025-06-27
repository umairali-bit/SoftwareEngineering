package com.libraryManagementSystem.libraryManagementSystem.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinTable(name = "author_id")
    private Author author;

    private LocalDateTime publishedDate = LocalDateTime.now().minusDays(1);



}
