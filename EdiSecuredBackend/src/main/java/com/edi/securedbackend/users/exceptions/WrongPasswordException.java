package com.edi.securedbackend.users.exceptions;

public class WrongPasswordException extends IllegalArgumentException {

	public WrongPasswordException(final String message) {
		super(message);
	}

}
