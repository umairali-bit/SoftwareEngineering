package com.libraryManagementSystem.libraryManagementSystem.repositories;


import com.libraryManagementSystem.libraryManagementSystem.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

//    @Query("SELECT b FROM BookEntity b LEFT JOIN FETCH b.authors WHERE b.id = :id")
//    Optional<BookEntity> findByIdWithAuthors(Long id);

    //option 1
//    Optional<BookEntity> findByTitle(String title);
    //option 2
    @Query("SELECT b FROM BookEntity b WHERE LOWER(b.title) = LOWER(:title)")
    Optional<BookEntity> findBookByTitleIgnoreCase(@Param("title") String title);

    @Query("SELECT b FROM BookEntity b WHERE b.publishedDate > :dateTime")
    List<BookEntity> findByPublishedDateAfter(@Param("dateTime") LocalDateTime dateTime);

    @Query("SELECT b FROM BookEntity b JOIN b.authors a WHERE a.id = :authorId")
    List<BookEntity> findBooksByAuthorsId(@Param("authorId") Long authorId);


}
