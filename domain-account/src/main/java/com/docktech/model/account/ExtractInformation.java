package com.docktech.model.account;

public class ExtractInformation {

	private Integer clientId;
	private String fromDate;
	private String toDate;
	private String agency;
	private String number;

	public ExtractInformation(Integer clientId, String fromDate, String toDate, String agency, String number) {
		super();
		this.clientId = clientId;
		this.fromDate = fromDate;
		this.agency = agency;
		this.number = number;
		this.toDate = toDate;
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

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
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
