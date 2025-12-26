package com.example.SpringBootDevTools.SpringBootDevTools.repositories;

import com.example.SpringBootDevTools.SpringBootDevTools.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
