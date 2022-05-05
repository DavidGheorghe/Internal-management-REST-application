package com.dvd.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.dvd.entity.order.ApplicationOrder;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
* Defines the Customer resource.
*
* @author David Gheorghe
*/
@Data
@Entity
@Table(name = "customer",
	uniqueConstraints = @UniqueConstraint(columnNames = {"email", "phoneNumber", "companyName", "cui", "billingAddress", "deliveryAddress"})
)
public class ApplicationCustomer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String companyName;
	private String cui;
	private String billingAddress;
	private String deliveryAddress;
	
	@JsonIgnore
	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
	private Set<ApplicationOrder> orders;
	
	public void addOrder(ApplicationOrder newOrder) {
		orders.add(newOrder);
	}
	
	public void removeOrder(ApplicationOrder removedOrder) {
		orders.remove(removedOrder);
	}
}
