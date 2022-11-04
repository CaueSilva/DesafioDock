package com.docktech.dto.account;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.docktech.domain.account.Account;

public class AccountDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer clientId;
	@NotEmpty(message="O preenchimento da agência é obrigatório.")
	@Length(min=4, max=4, message="Informe 4 números para agência.")
	private String agency;
	@NotEmpty(message="O preenchimento do número da conta é obrigatório.")
	@Length(min=7, max=7, message="Informe 7 números para o número da conta.")
	private String number;
	private float balance;
	private Integer status;
	private String statusDescription;
	
	public AccountDTO() {
		
	}
	
	public AccountDTO(Account account) {
		this.clientId = account.getClientId();
		this.agency = account.getAgency();
		this.number = account.getNumber();
		this.balance = account.getBalance();
		this.status = account.getStatus().getCode();
		this.setStatusDescription(account.getStatus().getDescription());
	}
	
	public AccountDTO(Integer clientId, String agency, String number, float balance) {
		this.clientId = clientId;
		this.agency = agency;
		this.number = number;	
		this.balance = balance;
	}
	
	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
	
}
