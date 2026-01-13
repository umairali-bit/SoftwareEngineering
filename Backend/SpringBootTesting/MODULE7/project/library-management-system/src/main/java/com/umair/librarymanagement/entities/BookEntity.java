package com.umair.librarymanagement.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDate publishedDate;

    @ManyToOne(fetch = FetchType.LAZY) //many books can be written by one author - eager by default
    @JoinColumn(name = "author_id") //FK
    private AuthorEntity author;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookEntity)) return false;
        BookEntity other = (BookEntity) o;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return 31; // stable constant
    }
}


