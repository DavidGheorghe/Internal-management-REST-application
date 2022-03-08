package com.dvd.DTO;

import java.util.Set;

import javax.validation.constraints.NotEmpty;

import com.dvd.entity.ApplicationPrivilege;

import lombok.Data;

/**
* Defines a DTO for a set of privileges.
*
* @author David Gheorghe
*/
@Data
public class ApplicationPrivilegeDTO {
	@NotEmpty
	private Set<ApplicationPrivilege> privileges;
}
