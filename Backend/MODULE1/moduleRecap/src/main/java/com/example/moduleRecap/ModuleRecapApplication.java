package com.example.moduleRecap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ModuleRecapApplication implements CommandLineRunner {

	@Autowired
	CakeBaker cakeBaker;

	public static void main(String[] args) {
		SpringApplication.run(ModuleRecapApplication.class, args);


	}
	@Override
	public void run(String... args) throws Exception {

		System.out.println(cakeBaker.getToppings());
		//System.out.println(cakeBaker.getSyrup());


	}
}
