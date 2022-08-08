package com.docktech.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.docktech.domain.Client;

@DataJpaTest
public class ClientRepositoryTeste {

	@Autowired
	TestEntityManager entityManager;
	
	@Autowired
	ClientRepository repo;
		
	
	@Test
	@DisplayName("Deve buscar um cliente caso o mesmo exista na base")
	public void mustFindClientThatExists() {
		String cpf = "12345678910";
		Client client = new Client(null, cpf, "Caio Jorge");
		entityManager.persist(client);
		Client savedClient = repo.findByCpf(cpf);
		assertThat(savedClient).isNotNull();
	}
	
	@Test
	@DisplayName("Deve retornar nulo ao buscar CPF inexistente na base")
	public void mustReturnErrorSearchingClientThatDoesntExist() {
		String cpf = "11111111111";
		Client client = new Client(null, "123456789100", "Caio Jorge");
		entityManager.persist(client);
		Client searchedClient = repo.findByCpf(cpf);
		assertThat(searchedClient).isNull();
	}
}
