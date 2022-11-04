package com.docktech.resources.account;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.docktech.services.account.AccountServices;

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
	public ResponseEntity<Account> getAccountByClientId(@PathVariable Integer clientId) {
		Account account = services.getAccountByClientId(clientId);
		return ResponseEntity.ok().body(account);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Account>> findAllAccounts() {
		List<Account> accounts = services.findAllAccounts();
		return ResponseEntity.ok().body(accounts);
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
	public ResponseEntity<List<AccountMovement>> getExtractAccount(@PathVariable Integer clientId,
			@RequestParam(value = "fromDate", defaultValue = "") String fromDate,
			@RequestParam(value = "toDate", defaultValue = "") String toDate) {
		List<AccountMovement> movements = services
				.extractAccount(new ExtractInformation(clientId, fromDate, toDate, "", ""));
		return ResponseEntity.ok().body(movements);
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
