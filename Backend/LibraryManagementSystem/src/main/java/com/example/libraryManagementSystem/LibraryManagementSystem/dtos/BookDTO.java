package com.example.libraryManagementSystem.LibraryManagementSystem.dtos;

import com.example.libraryManagementSystem.LibraryManagementSystem.annotations.ValidateBookTitle;
import com.example.libraryManagementSystem.LibraryManagementSystem.entities.AuthorEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookDTO {

    private Long id;

    // overriding the default message
    @ValidateBookTitle(message = "Title must be at least 3 characters long")
    private String title;

    private LocalDate publishedDate;

    private AuthorSummaryDTO author;


}
