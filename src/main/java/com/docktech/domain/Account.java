package com.docktech.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.docktech.domain.enums.AccountStatus;

@Entity
@IdClass(AccountKey.class)
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer clientId;
	@Id
	@Column(length=4)
	private String agency;
	@Id
	@Column(length=7) 
	private String number;
	private float balance;
	private Integer status;
	
	public Account() {
		
	}
	
	public Account(Integer clientId, String agency, String number, AccountStatus status) {
		this.clientId = clientId;
		this.agency = agency;
		this.number = number;
		this.balance = 0f;
		this.status = status.getCode();
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

	public AccountStatus getStatus() {
		return AccountStatus.toEnum(status);
	}

	public void setStatus(AccountStatus status) {
		this.status = status.getCode();
	}
	
}
