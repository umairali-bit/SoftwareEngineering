package com.umair.librarymanagement.repositories;


import com.umair.librarymanagement.entities.BookEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity,Long> {
    @EntityGraph(attributePaths = "author")
    Optional<BookEntity> findByTitle(String title);

    List<BookEntity> findByPublishedDateAfter(LocalDate date);
}
