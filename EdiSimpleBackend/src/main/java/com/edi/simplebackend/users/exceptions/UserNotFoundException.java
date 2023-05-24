package com.edi.simplebackend.users.exceptions;

public class UserNotFoundException extends IllegalArgumentException {
	public UserNotFoundException(final String message) {
		super(message);
	}

}
