package com.dvd.DTO;

import java.util.Set;

import com.dvd.entity.ApplicationPrivilege;

import lombok.Data;

/**
* Defines a DTO for a set of privileges.
*
* @author David Gheorghe
*/
@Data
public class ApplicationPrivilegeDTO {
	private Set<ApplicationPrivilege> privileges;
}
