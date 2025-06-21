package com.example.sortingAndPagination.sortingAndPagination.repositories;


import com.example.sortingAndPagination.sortingAndPagination.entities.ProductEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {


    List<ProductEntity> findByTitle(String title);
    List<ProductEntity> findByCreatedAtAfter(LocalDateTime after);
    List<ProductEntity> findByQuantityAndPrice(int quantity, BigDecimal price);

    List<ProductEntity> findByQuantityGreaterThanAndPriceLessThan(int quantity, BigDecimal price);
    List<ProductEntity> findByQuantityGreaterThanOrPriceLessThan(int quantity, BigDecimal price);
    List<ProductEntity> findByTitleLike(String title);

    List<ProductEntity> findByTitleContaining(String title);
    List<ProductEntity> findByTitleContainingIgnoreCase(String title);

    //   Optional<ProductEntity> findByTitleAndPrice(String title, BigDecimal price);

    @Query("select e from ProductEntity e where e.title = :title and e.price = :price")
    Optional<ProductEntity> findByTitleAndPrice(@Param("title") String title, @Param("price") BigDecimal price);

    //sorting
    List<ProductEntity> findByTitleOrderByPrice(String title);
    List<ProductEntity> findByOrderByPrice();

    //sorting by the Sort class
    List<ProductEntity> findBy(Sort sort);




}
