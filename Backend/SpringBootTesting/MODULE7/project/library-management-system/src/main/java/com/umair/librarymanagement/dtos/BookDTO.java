package com.umair.librarymanagement.dtos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umair.librarymanagement.annotations.ValidateBookTitle;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    @ValidateBookTitle(message = "Title must be at least 3 characters long")
    private String title;

    private LocalDate publishedDate;

    private AuthorSummaryDTO author;


}
