
package com.libraryManagementSystem.libraryManagementSystem.dto;


import com.libraryManagementSystem.libraryManagementSystem.entities.BookEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {

    private Long id;

    private String name;

    private List<BookDTO> books = new ArrayList<>();


}
