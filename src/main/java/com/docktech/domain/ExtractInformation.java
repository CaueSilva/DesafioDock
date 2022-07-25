package com.docktech.domain;

public class ExtractInformation {
	
	private Integer clientId;
	private String agency;
	private String number;
	private String fromDate;
	private String toDate;
	
	public ExtractInformation() {
		
	}

	public ExtractInformation(Integer clientId, String agency, String number, String fromDate, String toDate) {
		super();
		this.clientId = clientId;
		this.agency = agency;
		this.number = number;
		this.fromDate = fromDate;
		this.toDate = toDate;
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

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
	
}
