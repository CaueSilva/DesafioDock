package com.docktech.resources.security;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.docktech.domain.security.Credentials;
import com.docktech.dto.security.CredentialsDTO;
import com.docktech.services.security.SecurityServices;

@RestController
@RequestMapping(value="/api/auth")
public class SecurityResources {
	
	@Autowired
	private SecurityServices services;
	
	@RequestMapping(method=RequestMethod.POST)
	public  ResponseEntity<Credentials> createAuthorization(@Valid @RequestBody CredentialsDTO dto) {
		Credentials credentials = services.fromDTO(dto);
		credentials = services.insert(credentials);
		return ResponseEntity.ok().body(credentials);
	}

}
