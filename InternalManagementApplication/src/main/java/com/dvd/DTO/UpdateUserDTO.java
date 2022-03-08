package com.dvd.DTO;

import java.util.Set;

import org.hibernate.validator.constraints.Length;

import com.dvd.entity.ApplicationPrivilege;

import lombok.Data;

/**
* Defines the DTO for updating an user.
*
* @author David Gheorghe
*/
@Data
public class UpdateUserDTO {
	@Length(min = 5, max = 20)
	private String username;
	private Set<ApplicationPrivilege> privileges;
	private Set<Long> rolesIds;
}
