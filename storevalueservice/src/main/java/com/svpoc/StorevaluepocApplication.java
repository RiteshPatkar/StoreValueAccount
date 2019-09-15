package com.svpoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "com.svpoc" })
@EntityScan(basePackages = "com.svpoc.entity")
@EnableJpaRepositories(basePackages = "com.svpoc")
public class StorevaluepocApplication {

	public static void main(String[] args) {
		SpringApplication.run(StorevaluepocApplication.class, args);
	}

}
