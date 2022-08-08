package com.docktech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.docktech.logger.ApplicationLogger;

@SpringBootApplication
public class DesafiodockApplication {
	
	private static ApplicationLogger logger = new ApplicationLogger(DesafiodockApplication.class);

	public static void main(String[] args) {
		logger.logMessage("Iniciação da aplicação.");
		SpringApplication.run(DesafiodockApplication.class, args);
		logger.logMessage("Iniciação da aplicação finalizada.");
	}
}
