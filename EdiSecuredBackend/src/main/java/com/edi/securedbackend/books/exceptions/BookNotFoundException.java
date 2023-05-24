package com.edi.securedbackend.books.exceptions;

public class BookNotFoundException extends IllegalArgumentException {

	public BookNotFoundException(final String message) {
		super(message);
	}

}
