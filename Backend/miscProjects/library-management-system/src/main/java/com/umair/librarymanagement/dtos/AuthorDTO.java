package com.umair.librarymanagement.dtos;



import com.umair.librarymanagement.annotations.ValidateAuthorName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthorDTO {

    private Long id;
    @ValidateAuthorName
    private String name;
    private Set<BookSummaryDTO> books = new HashSet<>();
}
