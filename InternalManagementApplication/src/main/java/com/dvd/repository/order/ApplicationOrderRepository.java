package com.dvd.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dvd.entity.order.ApplicationOrder;

/**
* Defines the repository for the Order resource.
*
* @author David Gheorghe
*/
public interface ApplicationOrderRepository extends JpaRepository<ApplicationOrder, Long> {

}
