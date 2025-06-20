package com.example.jpaInterface.jpaInter;

import com.example.jpaInterface.jpaInter.entities.ProductEntity;
import com.example.jpaInterface.jpaInter.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class JpaInterfaceApplicationTests {

	@Autowired
	ProductRepository productRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void testRepository() {
		ProductEntity productEntity = ProductEntity.builder()
				.sku("nestle145")
				.title("Nestle Chocolate Milk")
				.price(BigDecimal.valueOf(45.23))
				.quantity(15)
				.build();

		ProductEntity savedEntity = productRepository.save(productEntity);
		System.out.println(savedEntity);
	}

}
