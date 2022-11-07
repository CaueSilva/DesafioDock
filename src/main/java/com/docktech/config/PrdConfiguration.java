package com.docktech.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.docktech.services.configuration.test.DataBasePrd;

@Configuration
@Profile("prd")
public class PrdConfiguration {
	
	@Autowired
	private DataBasePrd dataBasePrd;
	
	@Bean
	public boolean startDataBaseTest() {
		
		dataBasePrd.startDataBase();
		
		return true;
	}
		
}
