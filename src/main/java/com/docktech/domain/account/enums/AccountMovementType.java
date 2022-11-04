package com.docktech.domain.account.enums;

public enum AccountMovementType {
	
	DEBIT(0, "Débito - Saque"),
	CREDIT(1, "Crédito - Depósito");
	
	private Integer code;
	private String description;
	
	private AccountMovementType(Integer code, String description) {
		this.code = code;
		this.description = description;
	}
	
	public Integer getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public static AccountMovementType toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		for(AccountMovementType accountMovementType: AccountMovementType.values()) {
			if(cod.equals(accountMovementType.getCode())) {
				return accountMovementType;
			}
		}
		throw new IllegalArgumentException("Id inválido: "+cod);
	}

}
