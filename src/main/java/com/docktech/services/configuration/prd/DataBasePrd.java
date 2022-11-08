package com.docktech.services.configuration.prd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.docktech.domain.security.Credentials;
import com.docktech.repository.SecurityRepository;

@Service
public class DataBasePrd {
		
	@Autowired
	private SecurityRepository securityRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public void startDataBase() {
			
		Credentials credentials = new Credentials("04657801811", passwordEncoder.encode("$enhaLonga321$*1"), 2);
		
		securityRepository.save(credentials);
		
	}
	
}
