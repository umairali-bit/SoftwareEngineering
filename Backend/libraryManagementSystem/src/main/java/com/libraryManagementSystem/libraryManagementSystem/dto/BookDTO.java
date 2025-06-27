package com.libraryManagementSystem.libraryManagementSystem.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookDTO {

    private Long id;

    private String name;

    private LocalDateTime publishedDate;

    private List<AuthorDTO> authors = new ArrayList<>();
}
