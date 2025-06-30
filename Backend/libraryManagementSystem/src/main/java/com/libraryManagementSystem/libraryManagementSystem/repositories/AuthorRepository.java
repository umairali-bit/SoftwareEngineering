
package com.libraryManagementSystem.libraryManagementSystem.repositories;


import com.libraryManagementSystem.libraryManagementSystem.entities.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

    @Query("SELECT a FROM AuthorEntity a WHERE LOWER(a.name) = LOWER(:name)")
    Optional<AuthorEntity> findAuthorByName(@Param("name") String name);

    @Query("SELECT DISTINCT a FROM AuthorEntity a LEFT JOIN FETCH a.books")
    List<AuthorEntity> findAllAuthorsWithBooks();

}
