package com.dvd.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

/**
* Defines the exception for the case when a privilege cannot be removed from a user. 
*
* @author David Gheorghe
*/
@Getter
@ResponseStatus(code = HttpStatus.CONFLICT)
public class RemovePrivilegeFromUserException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String privilege;
	private String username;
	
	public RemovePrivilegeFromUserException(String privilege, String username) {
		super(String.format("Privilege %s cannot be removed from user %s because the user has a role that contains this privilege!", privilege, username));
		this.privilege = privilege;
		this.username = username;
	}
}
