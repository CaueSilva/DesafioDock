package com.docktech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.docktech.domain.security.Credentials;

public interface SecurityRepository extends JpaRepository<Credentials, String> {

	public Credentials findByCpf(String cpf);
	public boolean existsByCpf(String cpf);
	
}
