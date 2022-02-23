package com.dvd.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.Getter;

/**
* Defines the User resource.
*
* @author David Gheorghe
*/
@Data
@Entity
@Table(name = "user",
		uniqueConstraints = @UniqueConstraint(columnNames = {"username"})
		)
public class ApplicationUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	private Long id;
	private String username;
	private String password;
	
	@Column(name = "privilege")
	@ElementCollection(targetClass = ApplicationPrivilege.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "user_privilege", joinColumns = @JoinColumn(name = "user_id"))
	@Enumerated(EnumType.STRING)
	private Set<ApplicationPrivilege> privileges;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
			)
	@JsonIgnore
	private Set<ApplicationRole> roles;
	
	public ApplicationUser(String username, String password, Set<ApplicationRole> roles) {
		this.username = username;
		this.password = password;
		this.roles = roles;
		this.privileges = getPrivilegesFromRoles();
	}
	
	public ApplicationUser() {
		
	}
	
	/**
	 * Convert the user's privileges into authorities and return them.
	 * 
	 * @return a set of authorities.
	 */
	public Set<SimpleGrantedAuthority> getAuthorities() {
		Set<SimpleGrantedAuthority> authorities  = new HashSet<>();
		this.getPrivileges().stream().map(privilege -> authorities.add(new SimpleGrantedAuthority(privilege.name()))).collect(Collectors.toSet());
		return authorities;
	}
	
	/**
	 * Adds a new privilege to a user instance.
	 * 
	 * @param privilege - the new privilege
	 */
	public void addPrivilege(ApplicationPrivilege privilege) {
		this.getPrivileges().add(privilege);
	}
	
	
	/**
	 * Get the privileges from each role that the user have. Note: the user can have some privileges that the role does not have.
	 * 
	 * @return a set of privileges.
	 */
	private Set<ApplicationPrivilege> getPrivilegesFromRoles() {
		Set<ApplicationPrivilege> privileges = new HashSet<>();
		this.getRoles().stream().map(role -> privileges.addAll(role.getPrivileges())).collect(Collectors.toSet());
		return privileges;
	}
	
}
