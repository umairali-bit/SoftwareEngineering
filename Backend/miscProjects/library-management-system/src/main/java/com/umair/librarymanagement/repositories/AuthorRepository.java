package com.umair.librarymanagement.repositories;


import com.umair.librarymanagement.entities.AuthorEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<AuthorEntity,Long> {
    @EntityGraph(attributePaths = "books") //also loads book relationship eagerly
    Optional<AuthorEntity> findByNameIgnoreCase(String name);

    @Query("select a from AuthorEntity a left join fetch a.books where a.id = :id")
    Optional<AuthorEntity> findByIdWithBooks(Long id);



}
