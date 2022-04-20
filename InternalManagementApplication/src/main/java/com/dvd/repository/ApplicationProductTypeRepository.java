package com.dvd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dvd.entity.ApplicationProductType;

/**
* Defines the repository layer for the ProductType resource.
*
* @author David Gheorghe
*/
public interface ApplicationProductTypeRepository extends JpaRepository<ApplicationProductType, Long> {
	Boolean existsByTypeName(String name);
}