package com.docktech.services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.docktech.domain.account.Account;
import com.docktech.domain.account.AccountMovement;
import com.docktech.domain.account.enums.AccountStatus;
import com.docktech.dto.account.AccountDTO;
import com.docktech.dto.account.AccountMovementDTO;
import com.docktech.logger.ApplicationLogger;
import com.docktech.model.account.ExtractInformation;
import com.docktech.repository.AccountRepository;
import com.docktech.services.client.ClientServices;
import com.docktech.services.exceptions.DataBaseException;
import com.docktech.services.exceptions.DataIntegrityException;
import com.docktech.services.exceptions.InvalidOperationException;
import com.docktech.services.exceptions.ObjectNotFoundException;

@Service
public class AccountServices {

	private static ApplicationLogger logger = new ApplicationLogger(AccountServices.class);
	@Autowired
	private ClientServices clientServices;
	@Autowired
	private AccountMovementServices accountMovementServices;
	@Autowired
	private AccountRepository repository;

	@Transactional
	public Account createAccount(Account account) {

		logger.logMessage("Iniciando criação de conta.");

		logger.logMessage("Validando se Cliente ID " + account.getClientId() + " existe.");
		clientServices.findById(account.getClientId());
		logger.logMessage("Cliente ID " + account.getClientId() + " encontrado.");

		logger.logMessage("Verificando se cliente já possui conta.");
		if (existsAccountByClientId(account.getClientId())) {
			
			logger.logMessage("Conta já existente para o portador.");
			throw new DataIntegrityException("Conta já existente para portador ID " + account.getClientId() + ".");
			
		}

		logger.logMessage("Verificando se agência e conta já existem.");
		if (existsAccountByAgencyAndNumber(account)) {
			
			logger.logMessage("Agência e conta já existem.");
			throw new DataIntegrityException("Agência e Conta informadas já existem");
			
		}

		logger.logMessage("Salvando informações da conta no banco de dados.");

		try {
			account = repository.save(account);
		} catch (Throwable e) {
			
			logger.logDataBaseError();
			throw new DataBaseException("Houve um erro na requisição. Tente novamente em alguns instantes.");
			
		}

		logger.logMessage("Conta " + account.toString() + " criada com sucesso.");

		return account;
	}

	private boolean existsAccountByClientId(Integer clientId) {
		try {
			return repository.existsByClientId(clientId);
		} catch (Throwable e) {
			
			logger.logDataBaseError();
			throw new DataBaseException("Houve um erro na requisição. Tente novamente em alguns instantes.");
			
		}
	}

	private boolean existsAccountByAgencyAndNumber(Account account) {
		try {
			return repository.existsByAgencyAndNumber(account.getAgency(), account.getNumber());
		} catch (Throwable e) {
			
			logger.logDataBaseError();
			throw new DataBaseException("Houve um erro na requisição. Tente novamente em alguns instantes.");
			
		}
	}

	private boolean existsAccountByClientIdAgencyAndNumber(AccountMovement movement) {
		try {
			return repository.existsByClientIdAndAgencyAndNumber(movement.getClientId(), movement.getAgency(),
					movement.getNumber());
		} catch (Throwable e) {
			
			logger.logDataBaseError();
			throw new DataBaseException("Houve um erro na requisição. Tente novamente em alguns instantes.");
			
		}
	}

	public Account getAccountByClientId(Integer clientId) {

		logger.logMessage("Iniciando busca de conta por ID Cliente " + clientId + ".");
		Optional<Account> account;

		try {
			account = repository.findByClientId(clientId);
		} catch (Throwable e) {
			
			logger.logDataBaseError();
			throw new DataBaseException("Houve um erro na requisição. Tente novamente em alguns instantes.");
			
		}

		logger.logMessage("Busca concluída com sucesso.");
		return account.orElseThrow(
				() -> new ObjectNotFoundException("Conta para o Cliente ID " + clientId + " não encontrada."));

	}

	public List<Account> findAllAccounts() {

		logger.logMessage("Iniciando busca de todas as contas.");
		List<Account> list = new ArrayList<>();

		try {
			list = repository.findAll();
		} catch (Throwable e) {
			
			logger.logDataBaseError();
			throw new DataBaseException("Houve um erro na requisição. Tente novamente em alguns instantes.");
			
		}

		logger.logMessage("Busca concluída com sucesso.");
		return list;

	}

	public Account changeAccountStatus(Account account) {

		logger.logMessage("Iniciando alteração de status, conta " + account.toString() + ".");
		Account accountWithNewStatus;

		try {
			accountWithNewStatus = repository.save(account);
		} catch (Throwable e) {
			
			logger.logDataBaseError();
			throw new DataBaseException("Houve um erro na requisição. Tente novamente em alguns instantes.");
			
		}

		logger.logMessage("Alteração de status de conta concluído com sucesso.");
		return accountWithNewStatus;

	}

	public List<AccountMovement> extractAccount(ExtractInformation extractInformation) {

		logger.logMessage(
				"Iniciando busca de conta por Cliente ID " + extractInformation.getClientId() + " para extração de movimentos na conta.");
		Account account = getAccountByClientId(extractInformation.getClientId());
		logger.logMessage("Conta " + account.toString() + " encontrada.");
		
		extractInformation.setAgency(account.getAgency());
		extractInformation.setNumber(account.getNumber());
		
		List<AccountMovement> list = new ArrayList<>();

		try {

			logger.logMessage("Iniciando extração de movimentos na conta.");
			list = accountMovementServices.extractAccountMovements(extractInformation);

			if (list == null || list.isEmpty()) {
				
				logger.logMessage("Não há movimentações na conta.");
				throw new ObjectNotFoundException("Não há movimentações na conta.");
			
			}

		} catch(ObjectNotFoundException e) {
			
			throw new ObjectNotFoundException(e.getMessage());
		
		} catch (ParseException e) {

			logger.logMessage("Ocorreu um erro ao realizar a conversão de datas.");
			e.printStackTrace();

		} catch (Throwable e) {
			
			logger.logDataBaseError();
			throw new DataBaseException("Houve um erro na requisição. Tente novamente em alguns instantes.");
			
		}

		logger.logMessage("Extração realizada com sucesso.");
		return list;

	}

	@Transactional
	public AccountMovement deposit(AccountMovement movement) {

		logger.logMessage("Iniciando depósito na conta Agência " + movement.getAgency() + ", Número "
				+ movement.getNumber() + ".");

		logger.logMessage("Validando a existência de conta por Client ID, agência e número.");
		if (!existsAccountByClientIdAgencyAndNumber(movement)) {
			throw new ObjectNotFoundException(
					"Agência e Conta para o Cliente ID " + movement.getClientId() + " não encontrada.");
		}

		logger.logMessage("Iniciando busca de conta por Client ID, agência e número.");
		Optional<Account> accountOptional = repository.findByClientIdAndAgencyAndNumber(movement.getClientId(),
				movement.getAgency(), movement.getNumber());
		Account account = accountOptional.get();

		logger.logMessage("Validando se a conta está ativa.");
		if (accountIsActive(account.getStatus().getCode())) {

			try {

				movement = accountMovementServices.deposit(movement);
				
				logger.logMessage("Alteração e salvamento de novo saldo da conta.");
				account.setBalance(account.getBalance() + movement.getMovementValue());

				repository.save(account);
				logger.logMessage("Alteração de saldo salva com sucesso.");

			} catch (Throwable e) {
				
				logger.logDataBaseError();
				throw new DataBaseException("Houve um erro na requisição. Tente novamente em alguns instantes.");
				
			}

		} else {
			throw new InvalidOperationException("Operação não permitida para status vigente da conta.");
		}

		logger.logMessage("Depósito concluído com sucesso.");
		return movement;

	}

	@Transactional
	public AccountMovement withdraw(AccountMovement movement) {

		logger.logMessage(
				"Iniciando saque da conta agência " + movement.getAgency() + ", número " + movement.getNumber() + ".");

		logger.logMessage("Validando a existência de conta por Client ID, agência e número.");
		if (!existsAccountByClientIdAgencyAndNumber(movement)) {
			throw new ObjectNotFoundException(
					"Agência e Conta para o Cliente ID " + movement.getClientId() + " não encontrada.");
		}

		logger.logMessage("Iniciando busca de conta por Client ID, agência e número.");
		Optional<Account> accountOptional = repository.findByClientIdAndAgencyAndNumber(movement.getClientId(),
				movement.getAgency(), movement.getNumber());
		Account account = accountOptional.get();

		logger.logMessage("Iniciando validação de status, saldo e limite.");
		validateAccountStatusBalanceAndLimit(account, movement);
		logger.logMessage("A conta está apta para realizar a operação");

		try {
			
			movement = accountMovementServices.withdraw(movement);

			logger.logMessage("Alteração e salvamento de novo saldo da conta.");
			account.setBalance(account.getBalance() - movement.getMovementValue());
			
			repository.save(account);
			logger.logMessage("Alteração de saldo salva com sucesso.");

		} catch (Throwable e) {
			
			logger.logDataBaseError();
			throw new DataBaseException("Houve um erro na requisição. Tente novamente em alguns instantes.");
			
		}

		logger.logMessage("Saque concluído com sucesso.");
		return movement;

	}

	private void validateAccountStatusBalanceAndLimit(Account account, AccountMovement movement) {
		try {

			logger.logMessage("Validando se conta está ativa para realizar operação.");
			if (!accountIsActive(account.getStatus().getCode())) {
				throw new InvalidOperationException(
						"Operação não permitida. Verifique o Status da conta para realizar a operação.");
			}

			logger.logMessage("Validando se conta possui saldo suficiente para realizar operação.");
			if (!accountHaveEnoughBalance(account, movement)) {
				throw new InvalidOperationException(
						"Operação não permitida. Verifique o Saldo da conta para realizar a operação.");
			}

			logger.logMessage("Validando se conta possui limite de saque suficiente para realizar operação.");
			if (!accountHaveEnoughWithdrawLimit(account, movement)) {
				throw new InvalidOperationException(
						"Operação não permitida. Verifique o Limite disponível da conta para realizar a operação.");
			}

		} catch (ParseException e) {

			logger.logMessage("Ocorreu um erro ao realizar a conversão de datas.");
			e.printStackTrace();

		}
	}

	private boolean accountIsActive(Integer code) {
		return code == 0;
	}

	private boolean accountHaveEnoughWithdrawLimit(Account account, AccountMovement movement) throws ParseException {
		Double sum = 0.0;

		try {
			sum = accountMovementServices.getWithdrawDailyLimit(account);
		} catch (Throwable e) {
			
			logger.logDataBaseError();
			throw new DataBaseException("Houve um erro na requisição. Tente novamente em alguns instantes.");
			
		}

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

			logger.logMessage("Ocorreu um erro ao realizar a conversão de datas.");
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
