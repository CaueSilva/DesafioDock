package com.docktech.resources.account;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.docktech.domain.account.Account;
import com.docktech.domain.account.AccountMovement;
import com.docktech.domain.account.enums.AccountStatus;
import com.docktech.dto.account.AccountDTO;
import com.docktech.dto.account.AccountMovementDTO;
import com.docktech.model.account.ExtractInformation;
import com.docktech.services.AccountServices;

@RestController
@RequestMapping(value = "/api/account")
public class AccountResources {

	@Autowired
	private AccountServices services;

	@RequestMapping(method = RequestMethod.POST, value = "/{clientId}")
	public ResponseEntity<Account> createAccountByClientId(@Valid @RequestBody AccountDTO accountDto,
			@PathVariable Integer clientId) {
		accountDto.setClientId(clientId);
		Account account = services.fromDto(accountDto);
		account = services.createAccount(account);
		return ResponseEntity.ok().body(account);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{clientId}")
	public ResponseEntity<AccountDTO> getAccountByClientId(@PathVariable Integer clientId) {
		Account account = services.getAccountByClientId(clientId);
		return ResponseEntity.ok().body(new AccountDTO(account));
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<AccountDTO>> findAllAccounts() {
		List<Account> accounts = services.findAllAccounts();
		List<AccountDTO> accountsDto = accounts.stream().map(account -> new AccountDTO(account))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(accountsDto);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{clientId}/status/{statusId}")
	public ResponseEntity<Account> lockAccount(@RequestBody AccountDTO accountDto, @PathVariable Integer clientId,
			@PathVariable Integer statusId) {
		Account account = services.getAccountByClientId(clientId);
		accountDto.setClientId(clientId);
		services.validateAccountByDto(account, accountDto);
		account.setStatus(AccountStatus.toEnum(statusId));
		account = services.changeAccountStatus(account);
		return ResponseEntity.ok().body(account);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/extract/{clientId}")
	public ResponseEntity<List<AccountMovementDTO>> getExtractAccount(@PathVariable Integer clientId,
			@RequestParam(value = "fromDate", defaultValue = "") String fromDate,
			@RequestParam(value = "toDate", defaultValue = "") String toDate) {
		List<AccountMovement> movements = services
				.extractAccount(new ExtractInformation(clientId, fromDate, toDate, "", ""));
		List<AccountMovementDTO> accountDto = movements.stream().map(movement -> new AccountMovementDTO(movement))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(accountDto);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/deposit")
	public ResponseEntity<AccountMovement> deposit(@Valid @RequestBody AccountMovementDTO movementDto) {
		movementDto.setMovementType(1);
		AccountMovement movement = services.accountMovementFromDto(movementDto);
		services.deposit(movement);
		return ResponseEntity.ok().body(movement);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/withdraw")
	public ResponseEntity<AccountMovement> withdraw(@Valid @RequestBody AccountMovementDTO movementDto) {
		movementDto.setMovementType(0);
		AccountMovement movement = services.accountMovementFromDto(movementDto);
		services.withdraw(movement);
		return ResponseEntity.ok().body(movement);
	}

}
