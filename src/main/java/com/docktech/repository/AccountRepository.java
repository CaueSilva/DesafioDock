package com.docktech.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.docktech.domain.account.Account;


@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
	
	public Optional<Account> findByClientId(Integer clientId);
	public Optional<Account> findByClientIdAndAgencyAndNumber(Integer clientId, String agency, String number);
	public boolean existsByClientId(Integer clientId);
	public boolean existsByAgencyAndNumber(String agency, String number);
	public boolean existsByClientIdAndAgencyAndNumber(Integer clientId, String agency, String number);
}
