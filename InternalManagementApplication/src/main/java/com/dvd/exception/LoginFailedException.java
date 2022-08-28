package com.dvd.exception;

/**
* Defines the exception when login fails.
*
* @author David Gheorghe
*/
public class LoginFailedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public LoginFailedException() {
	}

	public LoginFailedException(String message) {
		super(message);
	}
	
	
}
