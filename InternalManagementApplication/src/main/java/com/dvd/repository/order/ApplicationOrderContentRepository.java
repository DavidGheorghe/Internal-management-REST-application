package com.dvd.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dvd.entity.order.ApplicationOrderContent;

/**
* Defines the repository for the order content.
*
* @author David Gheorghe
*/
public interface ApplicationOrderContentRepository extends JpaRepository<ApplicationOrderContent, Long> {
	
}
