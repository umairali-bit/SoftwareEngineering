package com.librarymanagement.auth.repository;

import com.librarymanagement.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
