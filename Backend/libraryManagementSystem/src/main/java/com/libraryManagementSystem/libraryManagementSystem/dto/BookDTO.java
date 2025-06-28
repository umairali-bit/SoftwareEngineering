package com.libraryManagementSystem.libraryManagementSystem.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class BookDTO {

    private Long id;

    private String title;

    private LocalDateTime publishedDate;


    private List<AuthorDTO> authors = new ArrayList<>();
}
