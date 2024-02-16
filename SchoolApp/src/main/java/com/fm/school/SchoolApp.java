package com.fm.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.fm.school")
public class SchoolApp {

	public static void main(String[] args) {
		SpringApplication.run(SchoolApp.class, args);
	}

}
