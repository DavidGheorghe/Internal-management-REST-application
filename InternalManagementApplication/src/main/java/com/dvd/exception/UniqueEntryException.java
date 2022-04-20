package com.dvd.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

/**
* Defines an exception when a new entry is already taken.
*
* @author David Gheorghe
*/
@Getter
@ResponseStatus(code = HttpStatus.CONFLICT)
public class UniqueEntryException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String entryName;

	public UniqueEntryException(String entryName) {
		super(String.format("There is already an entry with value: '%s'.", entryName));
		this.entryName = entryName;
	}
}