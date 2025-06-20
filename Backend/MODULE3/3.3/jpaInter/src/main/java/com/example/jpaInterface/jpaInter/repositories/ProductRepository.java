package com.example.jpaInterface.jpaInter.repositories;

import com.example.jpaInterface.jpaInter.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {


    List<ProductEntity> findByTitle(String soft_drink);
    List<ProductEntity> findByCreatedAtAfter(LocalDateTime after);
    List<ProductEntity> findByQuantityAndPrice(int quantity, BigDecimal price);

    List<ProductEntity> findByQuantityGreaterThanAndPriceLessThan(int quantity, BigDecimal price);
    List<ProductEntity> findByQuantityGreaterThanOrPriceLessThan(int quantity, BigDecimal price);
    List<ProductEntity> findByTitleLike(String title);

    List<ProductEntity> findByTitleContaining(String title);
}
