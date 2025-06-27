package com.libraryManagementSystem.libraryManagementSystem.dto;


import com.libraryManagementSystem.libraryManagementSystem.entities.BookEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AuthorDTO {

    private Long id;

    private String name;

    private List<BookDTO> books = new ArrayList<>();


}
