package com.dvd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dvd.entity.product.ApplicationProduct;

/**
* Defines the repository for the Product resource.
*
* @author David Gheorghe
*/
public interface ApplicationProductRepository extends JpaRepository<ApplicationProduct, Long> {

}
