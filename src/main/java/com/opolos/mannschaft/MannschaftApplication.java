package com.opolos.mannschaft;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class MannschaftApplication {

	public static void main(String[] args) {
		SpringApplication.run(MannschaftApplication.class, args);
	}



}
