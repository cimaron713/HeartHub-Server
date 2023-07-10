package com.umc_spring.Heart_Hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HeartHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(HeartHubApplication.class, args);
	}

}
