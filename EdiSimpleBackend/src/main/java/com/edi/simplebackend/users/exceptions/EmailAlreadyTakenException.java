package com.edi.simplebackend.users.exceptions;

public class EmailAlreadyTakenException extends IllegalArgumentException {

	public EmailAlreadyTakenException(final String message) {
		super(message);
	}

}
