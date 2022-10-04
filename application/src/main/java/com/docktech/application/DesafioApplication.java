package com.docktech.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.docktech.repository")
@EntityScan(basePackages = "com.docktech")
@ComponentScan(basePackages = "com.docktech")
@SpringBootApplication(scanBasePackages= "com.docktech")
public class DesafioApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(DesafioApplication.class, args);
	}
	
}
