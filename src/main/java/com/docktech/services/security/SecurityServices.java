package com.docktech.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.docktech.domain.security.Credentials;
import com.docktech.dto.security.CredentialsDTO;
import com.docktech.logger.ApplicationLogger;
import com.docktech.repository.SecurityRepository;
import com.docktech.security.UserSecurity;
import com.docktech.services.exceptions.AuthorizationException;
import com.docktech.services.exceptions.DataBaseException;
import com.docktech.services.exceptions.DataIntegrityException;

@Service
public class SecurityServices {
	
	private static ApplicationLogger logger = new ApplicationLogger(SecurityServices.class);
	
	@Autowired
	private SecurityRepository repo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public Credentials insert(Credentials credentials) {
		logger.logMessage("Iniciando inserção de credenciais de usuário.");
		
		if(userExists(credentials.getCpf())){
			throw new DataIntegrityException("CPF já existente na base.");
		}
		
		try {
			credentials = repo.save(credentials);
		} catch (Throwable e) {
			
			logger.logDataBaseError();
			throw new DataBaseException("Houve um erro na requisição. Tente novamente em alguns instantes.");
			
		}

		logger.logMessage("Usuário salvo com sucesso.");
		
		return credentials;
	}
	
	public boolean userExists(String cpf) {
		return repo.existsByCpf(cpf);
	}
	
	public void checkIfIsTheSameCustomer(String cpf) {
		logger.logMessage("Verificação de permissão do usuário.");
		
		UserSecurity userSecurity = SecurityServices.authenticated();

		if (userSecurity == null || !userSecurity.getUsername().equals(cpf)) {
			logger.logMessage("Usuário não permitido para a ação.");
			throw new AuthorizationException("Usuário não permitido para a ação.");
		}
	}
	
	public static UserSecurity authenticated() {
		try {
			
			return (UserSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
		} catch(Exception e) {
			return null;
		}
	}
	
	public Credentials fromDTO(CredentialsDTO dto) {
		return new Credentials(dto.getCpf(), passwordEncoder.encode(dto.getSenha()));
	}


}
