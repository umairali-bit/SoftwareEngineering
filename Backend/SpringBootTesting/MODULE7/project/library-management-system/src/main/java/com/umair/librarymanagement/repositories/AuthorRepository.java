package com.umair.librarymanagement.repositories;


import com.umair.librarymanagement.entities.AuthorEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<AuthorEntity,Long> {
    @EntityGraph(attributePaths = "books")
    Optional<AuthorEntity> findByNameIgnoreCase(String name);


}
