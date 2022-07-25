package com.docktech.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docktech.domain.Account;
import com.docktech.domain.AccountMovement;
import com.docktech.domain.ExtractInformation;
//import com.docktech.domain.AccountKey;
import com.docktech.domain.enums.AccountMovementType;
import com.docktech.dto.AccountMovementDTO;
import com.docktech.repository.AccountMovementRepository;

@Service
public class AccountMovementServices {
	
	@Autowired
	private AccountMovementRepository repository;
	private SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public List<AccountMovement> extractAccountMovements(ExtractInformation extract) throws ParseException {
		if(!extract.getFromDate().isEmpty() || !extract.getToDate().isEmpty()) {
			return extractAccountMovementsByDate(extract);
		}
		return repository.findByClientIdAndAgencyAndNumber(extract.getClientId(), extract.getAgency(), extract.getNumber());
	}
	
	public List<AccountMovement> extractAccountMovementsByDate(ExtractInformation extract) throws ParseException {
		Date fromDate = new Date(), toDate = new Date();
		
		if (extract.getFromDate().isEmpty()) {
			List<Optional<AccountMovement>> movementMin = repository.searchMinMovementDate(extract.getClientId(), extract.getAgency(), extract.getNumber());
			fromDate = movementMin.isEmpty() ? fromDate : movementMin.get(0).get().getDateMovement();
		} else {
			fromDate = sdfFormat.parse(extract.getFromDate());
		}
				
		if (extract.getToDate().isEmpty()) {
			List<Optional<AccountMovement>> movementMax = repository.searchMaxMovementDate(extract.getClientId(), extract.getAgency(), extract.getNumber());
			toDate = movementMax.isEmpty() ? toDate : movementMax.get(0).get().getDateMovement();
		} else {
			toDate = sdfFormat.parse(extract.getToDate());
		}
		
		return repository.findByClientIdAndAgencyAndNumberAndDateMovementBetween(extract.getClientId(), extract.getAgency(), extract.getNumber(), fromDate, toDate);
	}
	
	public AccountMovement accountMovementFromDto(AccountMovementDTO dto) throws ParseException {
		return new AccountMovement(null, dto.getClientId(), dto.getAgency(), dto.getNumber(), dto.getMovementValue(), 
				AccountMovementType.toEnum(dto.getMovementType()), sdfFormat.parse(sdfFormat.format(new Date())));
	}
	
	public AccountMovement deposit(AccountMovement movement) {
		movement.setIdMovement(null);
		return repository.save(movement);
	}
	
	public AccountMovement withdraw(AccountMovement movement) {
		movement.setIdMovement(null);
		return repository.save(movement);
	}

	public Double getWithdrawDailyLimit(Account account) throws ParseException {
		Date dateLimit = sdfFormat.parse(sdfFormat.format(new Date()));
		List<AccountMovement> movementsList = (repository.findAllByClientIdAndAgencyAndNumberAndDateMovement(account.getClientId(), account.getAgency(), account.getNumber(), dateLimit));
		return movementsList
				.stream()
				.filter( movement -> movement.getMovementType() == 0 )
				.mapToDouble(AccountMovement::getMovementValue)
				.sum();
	}

}
