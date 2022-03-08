package com.dvd.DTO;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
* TODO: comment
*
* @author David Gheorghe
*/
@Data
public class UsernameAndPasswordAuthenticationRequest {
	@NotBlank
	private String username;
	@NotBlank
	private String password;
}
