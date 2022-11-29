package com.docktech.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.docktech.domain.account.Account;

@DataJpaTest
public class AccountRepositoryTeste {

	@Autowired
	TestEntityManager entityManager;
	
	@Autowired
	AccountRepository repo;
	
	@Test
	@DisplayName("Deve retornar a conta do cliente com sucesso")
	public void mustReturnClientsAccount() {
		
	}
	
	@Test
	@DisplayName("Deve retornar nulo ao procurar conta inexistente")
	public void mustReturnNullOnSearchByNonexistentAccount() {
		
	}
	
}
