package com.dvd.DTO;

import java.util.Set;

import lombok.Data;

/**
* Defines the DTO for creating a new user.
*
* @author David Gheorghe
*/
@Data
public class CreateUserDTO {
	private String username;
	private String password;
	private Set<Long> rolesIds;
}
