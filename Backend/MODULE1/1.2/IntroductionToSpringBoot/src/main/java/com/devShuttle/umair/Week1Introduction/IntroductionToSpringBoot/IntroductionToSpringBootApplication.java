package com.devShuttle.umair.Week1Introduction.IntroductionToSpringBoot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IntroductionToSpringBootApplication implements CommandLineRunner {

	/*
	@Autowired is a Spring annotation used for automatic dependency injection. It tells Spring to inject an instance
	of a Spring-managed bean (a class annotated with @Component, @Service, @Repository, etc.) into another class.
	 */
	@Autowired
	Apple obj;

	@Autowired
	Apple obj2;



	public static void main(String[] args) {
		SpringApplication.run(IntroductionToSpringBootApplication.class, args);

		Apple obj = new Apple();
		obj.eatApple();
	}

	@Override
	public void run(String... args) throws Exception {




		obj.eatApple();// without @autowired this will give us an error
		obj2.eatApple();

		System.out.println(obj.hashCode());
		System.out.println(obj2.hashCode());

	}
}
