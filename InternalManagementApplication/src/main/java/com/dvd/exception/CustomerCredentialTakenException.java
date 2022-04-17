package com.dvd.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

/**
* Defines the exception of a taken customer credential.
*
* @author David Gheorghe
*/
@Getter
@ResponseStatus(code = HttpStatus.CONFLICT)
public class CustomerCredentialTakenException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private String fieldName;
	private String credentialTaken;

	public CustomerCredentialTakenException(String fieldName, String credential) {
		super(String.format("There is a customer with %s: %s", fieldName, credential));
		this.fieldName = fieldName;
		this.credentialTaken = credential;
	}
}