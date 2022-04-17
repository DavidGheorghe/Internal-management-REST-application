package com.dvd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dvd.entity.ApplicationCustomer;

/**
* Defines the repository for the Customer resource.
*
* @author David Gheorghe
*/
public interface ApplicationCustomerRepository extends JpaRepository<ApplicationCustomer, Long> {
	Boolean existsByBillingAddress(String billingAddress);
	Boolean existsByCui(String cui);
	Boolean existsByDeliveryAddress(String deliveryAddress);
	Boolean existsByEmail(String email);
	Boolean existsByPhoneNumber(String phoneNumber);
	Boolean existsByCompanyName(String companyName);
}