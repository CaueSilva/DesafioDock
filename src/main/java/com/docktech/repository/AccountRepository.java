package com.docktech.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.docktech.domain.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
	
	public Optional<Account> findByClientId(Integer clientId);
	public Optional<Account> findByClientIdAndAgencyAndNumber(Integer clientId, String agency, String number);
	public Optional<Account> findByAgencyAndNumber(String agency, String number);
	
}
