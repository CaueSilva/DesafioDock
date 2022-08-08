package com.docktech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.docktech.domain.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
	
	public Client findByCpf(String cpf);
	public boolean existsByCpf(String cpf);
	
}
