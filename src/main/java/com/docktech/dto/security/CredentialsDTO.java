package com.docktech.dto.security;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class CredentialsDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message="O preenchimento do CPF obrigatório.")
	@Length(min=11, max=11, message="CPF inválido.")
	private String cpf;
	@NotEmpty
	private String senha;
	
	public CredentialsDTO() {
		
	}

	public CredentialsDTO(String cpf, String senha) {
		super();
		this.cpf = cpf;
		this.senha = senha;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
