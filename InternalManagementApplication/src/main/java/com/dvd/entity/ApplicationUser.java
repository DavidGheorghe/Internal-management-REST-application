package com.dvd.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.dvd.entity.order.ApplicationOrder;

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
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ApplicationTodo> todos;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(
			name="user_pinned_order",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns  = @JoinColumn(name = "order_id")
			)
	private List<ApplicationOrder> pinnedOrders;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ApplicationOrder> assignedOrders;
	
	@Column(name = "role")
	@ElementCollection(targetClass = ApplicationRole.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
	@Enumerated(EnumType.STRING)
	private Set<ApplicationRole> roles;
	
	public ApplicationUser(String username, String password, Set<ApplicationRole> roles) {
		this.username = username;
		this.password = password;
		this.roles = roles;
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
		this.getRoles().stream().map(role -> authorities.addAll(role.getAuthorities())).collect(Collectors.toSet());
		return authorities;
	}
	
	public void addPinnedOrder(ApplicationOrder order) {
		pinnedOrders.add(order);
	}
	
	public void removePinnedOrder(ApplicationOrder order) {
		pinnedOrders.remove(order);
	}
	
	public void addAssignedOrder(ApplicationOrder order) {
		assignedOrders.add(order);
	}
	
	public void removeAssignedOrder(ApplicationOrder order) {
		assignedOrders.remove(order);
	}
}
