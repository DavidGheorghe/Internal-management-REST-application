package com.dvd.entity;

import static com.dvd.entity.ApplicationPrivilege.ORDER_READ;
import static com.dvd.entity.ApplicationPrivilege.PRODUCT_READ;
import static com.dvd.entity.ApplicationPrivilege.USER_READ;
import static com.dvd.entity.ApplicationPrivilege.USER_READ_OWN;
import static com.dvd.entity.ApplicationPrivilege.USER_WRITE;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.dvd.exception.ResourceNotFoundException;

/**
* Defines the Role resource.
*
* @author David Gheorghe
*/
public enum ApplicationRole {
	EMPLOYEE(1, EnumSet.of(ORDER_READ, PRODUCT_READ, USER_READ_OWN)),
	MANAGER(2, EnumSet.complementOf(EnumSet.of(USER_READ, USER_WRITE))),
	ADMIN(3, EnumSet.of(USER_WRITE, USER_READ));
	
	
	private Integer id;
	private EnumSet<ApplicationPrivilege> privileges;
	
	ApplicationRole(Integer id, EnumSet<ApplicationPrivilege> privileges) {
		this.id = id;
		this.privileges = privileges;
	}
	
	public Integer getId() {
		return id;
	}
	
	public EnumSet<ApplicationPrivilege> getPrivileges() {
		return privileges;
	}
	
	/**
	 * Adds a privilege to a role instance.
	 
	 * @param privilege
	 */
	public void addPrivilegeToRole(ApplicationPrivilege privilege) {
		this.privileges.add(privilege);
	}

	/**
	 * Convert the privileges into authorities and return them.
	 * 
	 * @return a set of authorities.
	 */
	public Set<SimpleGrantedAuthority> getAuthorities() {
		Set<SimpleGrantedAuthority> authorities = this.getPrivileges().stream().map(privilege -> new SimpleGrantedAuthority(privilege.name())).collect(Collectors.toSet());
		authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		return authorities;
	}

	/**
	 * Checks if a role instance contains a certain privilege.
	 * 
	 * @param privilege - the privilege that is sought
	 * @return true if the role contains the privilege, false otherwise.
	 */
	public boolean hasPrivilege(ApplicationPrivilege privilege) {		
		return this.getPrivileges().contains(privilege);
	}
	
	public static EnumSet<ApplicationRole> getRolesFromIds(Set<Integer> rolesIds) {
		EnumSet<ApplicationRole> roles = EnumSet.noneOf(ApplicationRole.class);
		for (Integer roleId: rolesIds) {
			ApplicationRole desiredRole = getRoleById(roleId);
			roles.add(desiredRole);
		}
		return roles;
	}
	
	public static ApplicationRole getRoleById(Integer id) {
		for (ApplicationRole role : values()) {
			if (role.getId() == id) {
				return role;
			}
		}
		throw new ResourceNotFoundException("Role", "id", String.valueOf(id));
	}
}
