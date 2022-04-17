package com.dvd.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
	
	// TODO: add set of orders
}
