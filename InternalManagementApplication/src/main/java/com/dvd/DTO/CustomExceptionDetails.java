package com.dvd.DTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
* Defines the DTO representation of an error that is sent in a response.
*
* @author David Gheorghe
*/
@AllArgsConstructor
@Getter
public class CustomExceptionDetails {
	private Date timestamp;
	private String message;
	private String details;
}
