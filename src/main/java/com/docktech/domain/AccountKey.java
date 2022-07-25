package com.docktech.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountKey implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@JoinColumn(name = "client_id")
	private Integer clientId;
	private String agency;
	private String number;

	public AccountKey() {

	}

	public AccountKey(Integer clientId, String agency, String number) {
		this.clientId = clientId;
		this.agency = agency;
		this.number = number;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		AccountKey key = (AccountKey) o;
		return clientId.equals(key.clientId) && agency.equals(key.agency) && number.equals(key.number);
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(clientId, agency, number);
    }
	
}
