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
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
* Defines the Role resource.
*
* @author David Gheorghe
*/
@Getter
@NoArgsConstructor
@Entity
@Table(name = "role",
		uniqueConstraints = @UniqueConstraint(columnNames = {"name"})
		)
public class ApplicationRole {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Setter
	private String name;
	
	@Setter
	@Column(name = "privilege")
	@ElementCollection(targetClass = ApplicationPrivilege.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "role_privilege", joinColumns = @JoinColumn(name = "role_id"))
	@Enumerated(EnumType.STRING)
	private Set<ApplicationPrivilege> privileges;
	
	@ManyToMany(mappedBy = "roles")
	@JsonIgnore
	private Set<ApplicationUser> users;
	
	public ApplicationRole(String name) {
		this.name = name;
		this.privileges = new HashSet<>();
		this.users = new HashSet<>();
	}
	
	public ApplicationRole(String name, Set<ApplicationPrivilege> privileges) {
		this.name = name;
		this.privileges = privileges;
		this.users = new HashSet<>();
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
		authorities.add(new SimpleGrantedAuthority("ROLE_" + this.getName()));
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
}
