package com.docktech.config.prd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.docktech.services.configuration.prd.DataBasePrd;

@Configuration
@Profile("prd")
public class PrdConfiguration {
	
	@Autowired
	private DataBasePrd dataBasePrd;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String dataBaseStrategy;
	
	@Bean
	public boolean startDataBasePrd() {
		
		if(dataBaseStrategy.equals("create")) {
			dataBasePrd.startDataBase();
			return true;
		}
		
		return false;
	}
		
}
