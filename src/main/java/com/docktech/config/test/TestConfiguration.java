package com.docktech.config.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.docktech.services.configuration.test.DataBaseTest;

@Configuration
@Profile({"test","dev"})
public class TestConfiguration {
	
	@Autowired
	private DataBaseTest dataBaseTest;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String dataBaseStrategy;
	
	@Bean
	public boolean startDataBaseTest() {
		
		if(dataBaseStrategy.equals("create")) {
			dataBaseTest.startDataBase();
			return true;
		}
		
		return false;
	}
		
}
