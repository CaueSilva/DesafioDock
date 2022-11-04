package com.docktech.services.configuration.test;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.docktech.domain.client.Client;
import com.docktech.domain.security.Credentials;
import com.docktech.repository.ClientRepository;
import com.docktech.repository.SecurityRepository;

@Service
public class DataBaseTest {
		
	@Autowired
	private SecurityRepository securityRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public void startDataBase() {
			
		Credentials credentials1 = new Credentials("04954703190", passwordEncoder.encode("123"), 2);
		Credentials credentials2 = new Credentials("93545103228", passwordEncoder.encode("321"), 1);
		Credentials credentials3 = new Credentials("06843219807", passwordEncoder.encode("123"), 1);
		
		securityRepository.saveAll(Arrays.asList(credentials1, credentials2, credentials3));
		
		Client client1 = new Client(null, "04954703190", "Caio Ribeiro");
		Client client2 = new Client(null, "93545103228", "Ronaldo Lazário");
		Client client3 = new Client(null, "06843219807", "Harry Potter");
		
		clientRepository.saveAll(Arrays.asList(client1, client2, client3));
		
	}
	
}
