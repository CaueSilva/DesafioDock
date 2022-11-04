package com.docktech.domain.account.enums;

public enum AccountStatus {
	
	UNLOCKED(0, "Desbloqueada"),
	LOCKED(1, "Bloqueada"),
	CLOSED(2, "Fechada");
	
	private Integer code;
	private String description;
	
	private AccountStatus(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	public Integer getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public static AccountStatus toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		for(AccountStatus accountStatus: AccountStatus.values()) {
			if(cod.equals(accountStatus.getCode())) {
				return accountStatus;
			}
		}
		throw new IllegalArgumentException("Id inválido: "+cod+". Válidos: 0 - Desbloqueio, 1 - Bloqueio, 3 - Cancelamento.");
	}

}
