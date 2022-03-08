package com.dvd.DTO;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

/**
* Defines the DTO for creating a new user.
*
* @author David Gheorghe
*/
@Data
public class CreateUserDTO {
	@NotBlank
	@Length(min = 3, max = 20)
	private String username;
	@NotBlank
	@Length(min = 5, max = 20)
	private String password;
	@NotEmpty
	private Set<Long> rolesIds;
}
