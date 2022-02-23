package com.dvd.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

/**
* Defines the exception of a taken username.
*
* @author David Gheorghe
*/
@Getter
@ResponseStatus(code = HttpStatus.CONFLICT)
public class UsernameTakenException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String username;

	public UsernameTakenException(String username) {
		super(String.format("Username %s taken!", username));
		this.username = username;
	}
}
