package com.docktech.services.account;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docktech.domain.account.Account;
import com.docktech.domain.account.AccountMovement;
import com.docktech.domain.account.enums.AccountMovementType;
import com.docktech.dto.account.AccountMovementDTO;
import com.docktech.logger.ApplicationLogger;
import com.docktech.model.account.ExtractInformation;
import com.docktech.repository.AccountMovementRepository;
import com.docktech.services.exceptions.DataBaseException;

@Service
public class AccountMovementServices {

	private static ApplicationLogger logger = new ApplicationLogger(AccountMovementServices.class);

	@Autowired
	private AccountMovementRepository repository;
	private SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyy-MM-dd");

	public List<AccountMovement> extractAccountMovements(ExtractInformation extract) throws ParseException {
		try {

			if (!extract.getFromDate().isEmpty() || !extract.getToDate().isEmpty()) {
				return extractAccountMovementsByDate(extract);
			}
			return repository.findByClientIdAndAgencyAndNumber(extract.getClientId(), extract.getAgency(),
					extract.getNumber());

		} catch (DataBaseException e) {
			
			logger.logDataBaseError();
			logger.logMessage(e.getMessage());
			throw new DataBaseException("Houve um erro na requisição. Tente novamente em alguns instantes.");
		}

	}

	private List<AccountMovement> extractAccountMovementsByDate(ExtractInformation extract) throws ParseException {
		try {
			
			Date fromDate = new Date(), toDate = new Date();

			if (extract.getFromDate().isEmpty()) {

				logger.logMessage("Iniciando busca de menor data de movimentação na conta para extração.");
				List<Optional<AccountMovement>> movementMin = repository.searchMinMovementDate(extract.getClientId(),
						extract.getAgency(), extract.getNumber());
				logger.logMessage("Busca concluída com sucesso.");

				fromDate = movementMin.isEmpty() ? fromDate : movementMin.get(0).get().getDateMovement();
				logger.logMessage("");

			} else {
				fromDate = sdfFormat.parse(extract.getFromDate());
			}

			if (extract.getToDate().isEmpty()) {

				logger.logMessage("Iniciando busca da maior data de movimentação na conta para extração.");
				List<Optional<AccountMovement>> movementMax = repository.searchMaxMovementDate(extract.getClientId(),
						extract.getAgency(), extract.getNumber());
				logger.logMessage("Busca concluída com sucesso.");

				toDate = movementMax.isEmpty() ? toDate : movementMax.get(0).get().getDateMovement();

			} else {
				toDate = sdfFormat.parse(extract.getToDate());
			}

			logger.logMessage("Iniciando busca de movimentações por data.");
			List<AccountMovement> list = repository.findByClientIdAndAgencyAndNumberAndDateMovementBetween(
					extract.getClientId(), extract.getAgency(), extract.getNumber(), fromDate, toDate);
			logger.logMessage("Busca concluída com sucesso.");

			return list;

		} catch (DataBaseException e) {
			
			throw new DataBaseException("Houve um erro na requisição. Tente novamente em alguns instantes.");
			
		}
	}

	public AccountMovement accountMovementFromDto(AccountMovementDTO dto) throws ParseException {
		return new AccountMovement(null, dto.getClientId(), dto.getAgency(), dto.getNumber(), dto.getMovementValue(),
				AccountMovementType.toEnum(dto.getMovementType()), sdfFormat.parse(sdfFormat.format(new Date())));
	}

	public AccountMovement deposit(AccountMovement movement) {
		movement.setIdMovement(null);

		logger.logMessage("Iniciando salvamento do depósito em conta.");

		try {
			movement = repository.save(movement);
		} catch (Throwable e) {
			
			logger.logDataBaseError();
			logger.logMessage(e.getMessage());
			throw new DataBaseException("Houve um erro na requisição. Tente novamente em alguns instantes.");
			
		}

		logger.logMessage("Depósito salvo com sucesso.");

		return movement;

	}

	public AccountMovement withdraw(AccountMovement movement) {
		movement.setIdMovement(null);

		logger.logMessage("Iniciando salvamento do saque em conta.");

		try {
			movement = repository.save(movement);
		} catch (Throwable e) {
			
			logger.logDataBaseError();
			logger.logMessage(e.getMessage());
			throw new DataBaseException("Houve um erro na requisição. Tente novamente em alguns instantes.");
			
		}

		logger.logMessage("Saque salvo com sucesso.");

		return movement;

	}

	public Double getWithdrawDailyLimit(Account account) throws ParseException {

		Date dateLimit = sdfFormat.parse(sdfFormat.format(new Date()));

		logger.logMessage(
				"Iniciando busca de movimentações na conta na data para verificar limite diário de saque da conta.");
		List<AccountMovement> movementsList;

		try {
			movementsList = (repository.findAllByClientIdAndAgencyAndNumberAndDateMovement(account.getClientId(),
					account.getAgency(), account.getNumber(), dateLimit));
		} catch (Throwable e) {
			
			logger.logDataBaseError();
			logger.logMessage(e.getMessage());
			throw new DataBaseException("Houve um erro na requisição. Tente novamente em alguns instantes.");
			
		}

		logger.logMessage("Busca concluída com sucesso.");

		return movementsList.stream().filter(movement -> movement.getMovementType() == 0)
				.mapToDouble(AccountMovement::getMovementValue).sum();

	}

}
