package com.example.libraryManagementSystem.LibraryManagementSystem.repositories;

import com.example.libraryManagementSystem.LibraryManagementSystem.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity,Long> {

    List<BookEntity> findByAuthorId(Long authorId);
}
