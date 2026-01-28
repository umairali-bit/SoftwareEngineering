package com.example.testing.testingapp;

import com.example.testing.testingapp.services.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@SpringBootApplication
public class TestingApplication implements CommandLineRunner {

    private final DataService dataService;

	public static void main(String[] args) {
		SpringApplication.run(TestingApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        System.out.println("The Data is: " + dataService.getData());

    }
}
