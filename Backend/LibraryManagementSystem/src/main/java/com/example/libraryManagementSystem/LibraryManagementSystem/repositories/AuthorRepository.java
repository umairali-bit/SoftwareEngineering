package com.example.libraryManagementSystem.LibraryManagementSystem.repositories;

import com.example.libraryManagementSystem.LibraryManagementSystem.entities.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<AuthorEntity,Long> {

}
