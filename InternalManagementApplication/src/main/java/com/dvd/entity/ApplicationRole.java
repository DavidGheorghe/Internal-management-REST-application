package com.dvd.entity;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.dvd.exception.ResourceNotFoundException;

/**
* Defines the Role resource.
*
* @author David Gheorghe
*/
public enum ApplicationRole {
	EMPLOYEE(1),
	SUPERVISOR(2),
	ADMIN(3);
	
	private Integer id;
	
	ApplicationRole(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}

	/**
	 * Convert the privileges into authorities and return them.
	 * 
	 * @return a set of authorities.
	 */
	public Set<SimpleGrantedAuthority> getAuthorities() {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		return authorities;
	}
	
	/**
	 * Returns the roles from the corresponding ids.
	 * 
	 * @param rolesIds - the ids.
	 * @return An enum set with the roles.
	 */
	public static EnumSet<ApplicationRole> getRolesFromIds(Set<Integer> rolesIds) {
		EnumSet<ApplicationRole> roles = EnumSet.noneOf(ApplicationRole.class);
		for (Integer roleId: rolesIds) {
			ApplicationRole desiredRole = getRoleById(roleId);
			roles.add(desiredRole);
		}
		return roles;
	}
	
	/**
	 * Returns a role by id. If the role is not found then an {@link com.dvd.exception.ResourceNotFoundException} is thrown.
	 * 
	 * 
	 * @param id - the id of the role
	 * @return The role.
	 */
	public static ApplicationRole getRoleById(Integer id) {
		for (ApplicationRole role : values()) {
			if (role.getId() == id) {
				return role;
			}
		}
		throw new ResourceNotFoundException("Role", "id", String.valueOf(id));
	}
}
