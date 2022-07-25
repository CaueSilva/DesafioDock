package com.docktech.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.docktech.domain.AccountMovement;

public interface AccountMovementRepository extends JpaRepository<AccountMovement, Integer> {
	
	List<AccountMovement> findByClientIdAndAgencyAndNumber(Integer clientId, String agency, String number);
	List<AccountMovement> findByClientIdAndAgencyAndNumberAndDateMovementBetween(Integer clientId, String agency, String number, Date start, Date end);
	List<AccountMovement> findAllByClientIdAndAgencyAndNumberAndDateMovement(Integer clientId, String agency, String number, Date date);
	
	@Query("SELECT a FROM AccountMovement a WHERE a.clientId IN (:clientId) AND a.agency LIKE %:agency% AND a.number LIKE %:number% ORDER BY a.idMovement ASC ")
	List<Optional<AccountMovement>> searchMinMovementDate(Integer clientId, String agency, String number);
	@Query("SELECT a FROM AccountMovement a WHERE a.clientId IN (:clientId) AND a.agency LIKE %:agency% AND a.number LIKE %:number% ORDER BY a.idMovement DESC ")
	List<Optional<AccountMovement>> searchMaxMovementDate(Integer clientId, String agency, String number);

}
