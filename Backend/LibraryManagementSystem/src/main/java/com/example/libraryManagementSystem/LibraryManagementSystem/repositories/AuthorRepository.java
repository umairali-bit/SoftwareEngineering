package com.example.libraryManagementSystem.LibraryManagementSystem.repositories;

import com.example.libraryManagementSystem.LibraryManagementSystem.entities.AuthorEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<AuthorEntity,Long> {
    @EntityGraph(attributePaths = "books")
    Optional<AuthorEntity> findByName(String name);


}
