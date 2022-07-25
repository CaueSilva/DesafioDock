package com.docktech.dto;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.docktech.domain.AccountMovement;
import com.docktech.domain.enums.AccountMovementType;

public class AccountMovementDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer idMovement;
	private Integer clientId;
	@NotEmpty(message="O preenchimento da Agência é obrigatório.")
	private String agency;
	@NotEmpty(message="O preenchimento do Número da Conta é obrigatório.")
	private String number;
	@Min(value=2, message="O valor mínimo para depósito/saque é de R$2 reais.")
	private float movementValue;
	private Integer movementType;
	private String description;
	private String dateMovement;
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	public AccountMovementDTO() {
		
	}
	
	public AccountMovementDTO(Integer clientId, String agency, String number) {
		this.clientId = clientId;
		this.agency = agency;
		this.number = number;
	}
	
	public AccountMovementDTO(AccountMovement accountMovement) {
		this.idMovement = accountMovement.getIdMovement();
		this.clientId = accountMovement.getClientId();
		this.agency = accountMovement.getAgency();
		this.number = accountMovement.getNumber();
		this.movementValue = accountMovement.getMovementValue();
		this.movementType = accountMovement.getMovementType();
		this.description = accountMovement.getDescription();
		this.dateMovement = format.format(accountMovement.getDateMovement());
	}
	
	public AccountMovementDTO(Integer clientId, String agency, String number, float movementValue, AccountMovementType type, Date dateMovement) {
		this.clientId = clientId;
		this.agency = agency;
		this.number = number;
		this.movementValue = movementValue;
		this.movementType = type.getCode();
		this.setDescription(AccountMovementType.toEnum(movementType).getDescription());
		this.setDateMovement(dateMovement);
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

	public float getMovementValue() {
		return movementValue;
	}

	public void setMovementValue(float movementValue) {
		this.movementValue = movementValue;
	}

	public Integer getMovementType() {
		return movementType;
	}

	public void setMovementType(Integer movementType) {
		this.movementType = movementType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getIdMovement() {
		return idMovement;
	}

	public void setIdMovement(Integer idMovement) {
		this.idMovement = idMovement;
	}

	public String getDateMovement() {
		return dateMovement;
	}

	public void setDateMovement(Date dateMovement) {
		this.dateMovement = format.format(dateMovement);
	}

}
