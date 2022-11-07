package com.docktech.domain.security;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.Length;

import com.docktech.security.enums.SecurityRoles;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Credentials implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(unique=true) 
	@Length(min=11, max=11, message="CPF inválido.")
	private String cpf;
	@JsonIgnore
	private String senha;
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="ROLES")
	private Set<Integer> roles = new HashSet<>();
	
	public Credentials() {
		addRole(SecurityRoles.CLIENT);
	}
	
	public Credentials(String cpf, String senha) {
		super();
		this.cpf = cpf;
		this.senha = senha;
		addRole(SecurityRoles.CLIENT);
	}
	
	public Credentials(String cpf, String senha, Integer role) {
		super();
		this.cpf = cpf;
		this.senha = senha;
		addRole(SecurityRoles.toEnum(role));
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Set<SecurityRoles> getRoles(){
		return roles.stream().map(roles -> SecurityRoles.toEnum(roles)).collect(Collectors.toSet());
	}
	
	public void addRole(SecurityRoles securityRole) {
		roles.add(securityRole.getCode());
	}
	
}
