package com.docktech.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.docktech.services.configuration.test.DataBaseTest;

@Configuration
@Profile("dev")
public class DevConfiguration {
	
	@Autowired
	private DataBaseTest dataBaseTest;
	
	@Bean
	public boolean startDataBaseTest() {
		
		dataBaseTest.startDataBase();
		
		return true;
	}
		
}
