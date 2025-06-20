package com.example.jpaInterface.jpaInter;

import com.example.jpaInterface.jpaInter.entities.ProductEntity;
import com.example.jpaInterface.jpaInter.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

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

	@Test
	void getRepository() {
		List<ProductEntity> entities = productRepository.findAll();
		System.out.println(entities);
	}

	@Test
	void getTitle() {
		List<ProductEntity> entities = productRepository.findByTitle("Soft Drink");
		System.out.println(entities);

	}

}
