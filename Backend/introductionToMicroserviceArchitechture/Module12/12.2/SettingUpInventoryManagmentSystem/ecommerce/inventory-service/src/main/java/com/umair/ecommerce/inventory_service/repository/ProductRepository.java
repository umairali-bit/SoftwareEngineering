package com.umair.ecommerce.inventory_service.repository;

import com.umair.ecommerce.inventory_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
