package com.docktech.services.configuration.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.docktech.domain.security.Credentials;
import com.docktech.repository.SecurityRepository;

@Service
public class DataBaseTest {
		
	@Autowired
	private SecurityRepository securityRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public void startDataBase() {
			
		Credentials credentials1 = new Credentials("04521872064", passwordEncoder.encode("$enhaLonga321$*1"), 2);
		
		securityRepository.save(credentials1);
		
	}
	
}
