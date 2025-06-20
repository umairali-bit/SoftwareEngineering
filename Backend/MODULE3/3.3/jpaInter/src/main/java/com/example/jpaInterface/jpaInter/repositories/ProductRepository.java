package com.example.jpaInterface.jpaInter.repositories;

import com.example.jpaInterface.jpaInter.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {


}
