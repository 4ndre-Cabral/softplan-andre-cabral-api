package com.softplan.desafio.domain.model;

public enum RoleEnum {
    ROLE_ADMIN,
    ROLE_TRIADOR,
	ROLE_FINALIZADOR;
	
	private static final RoleEnum[] copyOfValues = values();
	
	public static RoleEnum forName(String name) {
        for (RoleEnum value : copyOfValues) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        return null;
    }
}
