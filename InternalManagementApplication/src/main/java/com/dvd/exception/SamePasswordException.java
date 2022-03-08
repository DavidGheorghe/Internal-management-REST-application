package com.dvd.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
* TODO: comment
*
* @author David Gheorghe
*/
@ResponseStatus(code = HttpStatus.CONFLICT)
public class SamePasswordException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SamePasswordException(String message) {
		super(message);
	}
}
