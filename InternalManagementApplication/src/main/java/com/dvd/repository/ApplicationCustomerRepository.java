package com.dvd.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
	@Query("SELECT c FROM ApplicationCustomer c WHERE c.firstName LIKE '%' || ?1 || '%'"
			+ "OR c.lastName LIKE '%' || ?1 || '%' OR c.companyName LIKE '%' || ?1 || '%'"
			+ "OR c.email LIKE '%' || ?1 || '%' OR c.deliveryAddress LIKE '%' || ?1 || '%'"
			+ "OR c.billingAddress LIKE '%' || ?1 || '%' OR c.cui LIKE '%' || ?1 || '%'")
	Page<ApplicationCustomer> findByAllColumnsContains(String pattern, Pageable pageable);
}