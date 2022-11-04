package com.docktech.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.docktech.domain.security.Credentials;
import com.docktech.repository.SecurityRepository;
import com.docktech.security.UserSecurity;


@Service
public class UserDetailServiceImpl implements UserDetailsService{
	
	@Autowired
	private SecurityRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
		
		Credentials credential = repo.findByCpf(cpf);
		
		if(credential == null) {
			throw new UsernameNotFoundException(cpf);
		}
		
		return new UserSecurity(credential.getCpf(), credential.getSenha(), credential.getRoles());
	}

}
