package com.edi.simplebackend.users.exceptions;

public class UsernameAlreadyTakenException extends IllegalArgumentException{
	public UsernameAlreadyTakenException(final String message) {
		super(message);
	}
}
