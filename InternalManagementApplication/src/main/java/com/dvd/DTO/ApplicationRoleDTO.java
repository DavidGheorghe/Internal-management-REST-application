package com.dvd.DTO;

import java.util.Set;

import com.dvd.entity.ApplicationPrivilege;

import lombok.Data;

/**
* Defines the DTO representation of a role.
*
* @author David Gheorghe
*/
@Data
public class ApplicationRoleDTO {
	private Long id;
	private String name;
	private Set<ApplicationPrivilege> privileges;
}
