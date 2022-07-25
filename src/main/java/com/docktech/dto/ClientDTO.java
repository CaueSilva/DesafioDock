package com.docktech.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.docktech.domain.Client;

public class ClientDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer clientId;
	@NotEmpty(message="O preenchimento do CPF obrigatório.")
	@Length(min=11, max=11, message="CPF inválido.")
	private String cpf;
	@NotEmpty(message="O preenchimento do nome é obrigatório.")
	private String name;
	
	public ClientDTO(){
		
	}
	
	public ClientDTO(Client client) {
		this.clientId = client.getClienId();
		this.cpf = client.getCpf();
		this.name = client.getName();
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
