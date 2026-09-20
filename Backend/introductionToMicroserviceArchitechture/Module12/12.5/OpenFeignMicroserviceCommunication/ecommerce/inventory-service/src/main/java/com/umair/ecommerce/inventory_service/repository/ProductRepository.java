package com.umair.ecommerce.inventory_service.repository;

import com.umair.ecommerce.inventory_service.entity.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Product WHERE p.id = id ")
    Optional<Product> findByIdForUpdate(Long id);

}
