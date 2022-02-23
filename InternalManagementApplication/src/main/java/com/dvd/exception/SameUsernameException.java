package com.dvd.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

/**
* Defines the exception for the case a user tries to change its username with the same username.
*
* @author David Gheorghe
*/
@Getter
@ResponseStatus(code = HttpStatus.CONFLICT)
public class SameUsernameException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String username;

	public SameUsernameException(String username) {
		super(String.format("Cannot set same username as previous: %s!", username));
		this.username = username;
	}
}
