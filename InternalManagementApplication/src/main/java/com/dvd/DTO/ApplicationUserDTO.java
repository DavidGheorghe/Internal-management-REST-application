package com.dvd.DTO;

import java.util.Set;

import com.dvd.entity.ApplicationPrivilege;
import com.dvd.entity.ApplicationRole;

import lombok.Data;

/**
* Defines the DTO representation of an user.
*
* @author David Gheorghe
*/
@Data
public class ApplicationUserDTO {
	private Long id;
	private String username;
	private Set<ApplicationRole> roles;
}
