package com.docktech.security.enums;

public enum SecurityRoles {

	CLIENT(1, "ROLE_CLIENT"),
	ADMIN(2, "ROLE_ADMIN");

	private Integer code;
	private String description;

	private SecurityRoles(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	public Integer getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public static SecurityRoles toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		for (SecurityRoles securityRoles : SecurityRoles.values()) {
			if (cod.equals(securityRoles.getCode())) {
				return securityRoles;
			}
		}
		throw new IllegalArgumentException("Id inválido: " + cod);
	}

}
