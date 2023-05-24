package com.edi.securedbackend.users.exceptions;

public class UserNotFoundException extends IllegalArgumentException {

	public UserNotFoundException(final String message) {
		super(message);
	}

}
