package com.docktech.services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.docktech.domain.Account;
import com.docktech.domain.AccountMovement;
import com.docktech.domain.ExtractInformation;
import com.docktech.domain.enums.AccountStatus;
import com.docktech.dto.AccountDTO;
import com.docktech.dto.AccountMovementDTO;
import com.docktech.repository.AccountRepository;
import com.docktech.services.exceptions.DataIntegrityException;
import com.docktech.services.exceptions.InvalidOperationException;
import com.docktech.services.exceptions.ObjectNotFoundException;

@Service
public class AccountServices {

	@Autowired
	private ClientServices clientServices;
	@Autowired
	private AccountMovementServices accountMovementServices;
	@Autowired
	private AccountRepository repository;

	@Transactional
	public Account createAccount(Account account) {
		clientServices.findById(account.getClientId());
		try {
			Account existentAccount = getAccountByClientId(account.getClientId());
			if (existentAccount != null) {
				throw new DataIntegrityException("Conta já existente para portador ID " + account.getClientId() + ".");
				//validar se existe conta por agencia e conta, do contrário vai retornar dataintegrityexception
			}
		} catch (ObjectNotFoundException e) {
			validateIfAgencyAndNumberExists(account);
			account = repository.save(account);
		}
		return account;
	}
	
	public Account getAccountByClientId(Integer clientId) {
		Optional<Account> account = repository.findByClientId(clientId);
		return account.orElseThrow(
				() -> new ObjectNotFoundException("Conta para o Cliente ID " + clientId + " não encontrada."));
	}
	
	private void validateIfAgencyAndNumberExists(Account account) {
		Optional<Account> accountOpt = repository.findByAgencyAndNumber(account.getAgency(), account.getNumber());
		if( !accountOpt.isEmpty() ) {
			throw new DataIntegrityException("Agência e Conta informadas já existem");
		}
	}

	public List<Account> findAllAccounts() {
		return repository.findAll();
	}

	public Account changeAccountStatus(Account account) {
		return repository.save(account);
	}

	public List<AccountMovement> extractAccount(Integer clientId, String fromDate, String toDate) {
		Account account = getAccountByClientId(clientId);
		ExtractInformation extract = new ExtractInformation(account.getClientId(), account.getAgency(), account.getNumber(), fromDate, toDate);  
		List<AccountMovement> list = new ArrayList<>();
		try {
			list = accountMovementServices.extractAccountMovements(extract);
			if (list==null || list.isEmpty()) {
				throw new ObjectNotFoundException("Não há movimentações na conta.");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Transactional
	public AccountMovement deposit(AccountMovement movement) {
		Optional<Account> accountOptional = repository.findByClientIdAndAgencyAndNumber(movement.getClientId(),
				movement.getAgency(), movement.getNumber());
		if (accountOptional.isPresent()) {
			Account account = accountOptional.get();
			if (accountIsActive(account.getStatus().getCode())) {
				movement = accountMovementServices.deposit(movement);
				account.setBalance(account.getBalance() + movement.getMovementValue());
				repository.save(account);
			} else {
				throw new InvalidOperationException("Operação não permitida para status vigente da conta.");
			}
		} else {
			throw new ObjectNotFoundException(
					"Agência e Conta para o Cliente ID " + movement.getClientId() + " não encontrada.");
		}
		return movement;
	}

	@Transactional
	public AccountMovement withdraw(AccountMovement movement) {
		Optional<Account> accountOptional = repository.findByClientIdAndAgencyAndNumber(movement.getClientId(),
				movement.getAgency(), movement.getNumber());
		try {
			if (accountOptional.isPresent()) {
				Account account = accountOptional.get();
				if (accountIsActive(account.getStatus().getCode()) && 
						accountHaveEnoughBalance(account, movement) &&
						accountHaveEnoughWithdrawLimit(account, movement)) {

					account.setBalance(account.getBalance() - movement.getMovementValue());
					movement = accountMovementServices.withdraw(movement);
					repository.save(account);

				} else {
					throw new InvalidOperationException(
							"Operação não permitida. Verifique o Status, Saldo ou Limite disponível para realizar a operação.");
				}
			} else {
				throw new ObjectNotFoundException(
						"Agência e Conta para o Cliente ID " + movement.getClientId() + " não encontrada.");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return movement;
	}
	
	private boolean accountIsActive(Integer code) {
		return code == 0;
	}

	private boolean accountHaveEnoughWithdrawLimit(Account account, AccountMovement movement) throws ParseException {
		Double sum = accountMovementServices.getWithdrawDailyLimit(account);
		return (2000 - sum) >= 0 && (2000 - (movement.getMovementValue() + sum)) >= 0;
	}

	private boolean accountHaveEnoughBalance(Account account, AccountMovement movement) {
		return account.getBalance() - movement.getMovementValue() >= 0;
	}

	public Account fromDto(AccountDTO accountDto) {
		return new Account(accountDto.getClientId(), accountDto.getAgency(), accountDto.getNumber(),
				AccountStatus.toEnum(0));
	}

	public AccountMovement accountMovementFromDto(AccountMovementDTO dto) {
		try {
			return accountMovementServices.accountMovementFromDto(dto);
		} catch (ParseException e) {
			e.printStackTrace();
			return new AccountMovement();
		}
	}

	public void validateAccountByDto(Account account, AccountDTO accountDto) {
		if (account.getClientId() == accountDto.getClientId() && (!account.getAgency().equals(accountDto.getAgency())
				|| !account.getNumber().equals(accountDto.getNumber()))) {
			throw new InvalidOperationException(
					"A conta informada não pertence ao portador ID " + account.getClientId() + ".");
		}
	}

}
