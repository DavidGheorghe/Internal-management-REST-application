package com.dvd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dvd.entity.ApplicationProductCategory;

/**
* Defines the repository layer for the ProductCategory resource.
*
* @author David Gheorghe
*/
public interface ApplicationProductCategoryRepository extends JpaRepository<ApplicationProductCategory, Long> {
	Boolean existsByCategoryName(String name);
}