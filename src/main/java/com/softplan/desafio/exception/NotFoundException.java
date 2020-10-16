package com.softplan.desafio.exception;

public class NotFoundException extends Exception {
	
	private static final long serialVersionUID = -3498888276305436704L;

	public NotFoundException() {
		super("Não encontrado");
	}

	public NotFoundException(String message) {
		super(message);
	}
}
