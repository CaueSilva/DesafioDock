package com.docktech.domain.account;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.docktech.domain.account.enums.AccountMovementType;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class AccountMovement implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idMovement;
	private Integer clientId;
	private String agency;
	private String number;
	private float movementValue;
	private Integer movementType;
	private String description;
	@JsonFormat(pattern="dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date dateMovement;
	
	public AccountMovement() {
		
	}
	
	public AccountMovement(Integer idMovement, Integer clientId, String agency, String number, float movementValue, AccountMovementType type, Date dateMovement) {
		super();
		this.idMovement = idMovement;
		this.clientId = clientId;
		this.agency = agency;
		this.number = number;
		this.movementValue = movementValue;
		this.movementType = type.getCode();
		this.setDescription(AccountMovementType.toEnum(movementType).getDescription());
		this.setDateMovement(dateMovement);
	}
	
	public Integer getIdMovement() {
		return idMovement;
	}

	public void setIdMovement(Integer idMovement) {
		this.idMovement = idMovement;
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

	public Date getDateMovement() {
		return dateMovement;
	}

	public void setDateMovement(Date dateMovement) {
		this.dateMovement = dateMovement;
	}
	
}
