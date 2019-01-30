package com.gates.standstrong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class StandStrongApplication {

	public static void main(String[] args) {
		SpringApplication.run(StandStrongApplication.class, args);
	}
}
