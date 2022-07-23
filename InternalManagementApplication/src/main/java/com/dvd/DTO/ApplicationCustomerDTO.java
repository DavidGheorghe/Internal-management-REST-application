package com.dvd.DTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
*  Defines the DTO representation of a customer.
*
* @author David Gheorghe
*/
@Data
public class ApplicationCustomerDTO {
	private Long id;
	private String firstName;
	private String lastName;
	@Email
	private String email;
	private String phoneNumber;
	@NotBlank
	private String companyName;
	@NotBlank
	private String cui;
	@NotBlank
	private String billingAddress;
	@NotBlank
	private String deliveryAddress;
//	@JsonIgnore
//	private Set<RetrievedOrderDTO> orders;
	// TODO: create endpoint to get the orders.
}