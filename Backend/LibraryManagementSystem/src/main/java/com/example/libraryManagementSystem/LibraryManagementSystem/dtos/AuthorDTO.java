package com.example.libraryManagementSystem.LibraryManagementSystem.dtos;


import com.example.libraryManagementSystem.LibraryManagementSystem.entities.BookEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthorDTO {

    private Long id;
    private String name;
    private Set<BookEntity> books;
}
