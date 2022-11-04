package com.docktech.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.docktech.security.enums.SecurityRoles;

public class UserSecurity implements UserDetails{
	private static final long serialVersionUID = 1L;

	private String cpf;
	private String senha;
	private Collection<? extends GrantedAuthority> authorities;
		
	public UserSecurity() {
		
	}
	
	public UserSecurity(String cpf, String senha,
			Set<SecurityRoles> roles) {
		super();
		this.cpf = cpf;
		this.senha = senha;
		this.authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role.getDescription())).collect(Collectors.toList());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return cpf;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

		
	
}
