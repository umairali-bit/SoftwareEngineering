package com.example.cachingApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableCaching - we implemented in caching config instead
public class CachingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CachingAppApplication.class, args);
	}

}
