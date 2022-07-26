package com.dvd.repository.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dvd.entity.order.ApplicationOrder;
import com.dvd.entity.order.ApplicationOrderStatus;

/**
* Defines the repository for the Order resource.
*
* @author David Gheorghe
*/
public interface ApplicationOrderRepository extends JpaRepository<ApplicationOrder, Long> {
	@Query("Select o FROM ApplicationOrder o WHERE o.id LIKE '%' || ?1 || '%' "
			+ "OR o.customer.firstName LIKE '%' || ?1 || '%' "
			+ "OR o.customer.lastName LIKE '%' || ?1 || '%' "
			+ "OR o.customer.companyName LIKE '%' || ?1 || '%'")
	Page<ApplicationOrder> findByIdOrCustomerNameAndCompany(String pattern, Pageable pageable);
	
	@Query("Select o FROM ApplicationOrder o WHERE o.status = ?2 AND o.id LIKE '%' || ?1 || '%' "
			+ "OR o.customer.firstName LIKE '%' || ?1 || '%' "
			+ "OR o.customer.lastName LIKE '%' || ?1 || '%' "
			+ "OR o.customer.companyName LIKE '%' || ?1 || '%'")
	Page<ApplicationOrder> findByIdOrCustomerNameAndCompanyAndStatus(String pattern, ApplicationOrderStatus status, Pageable pageable);
	
	@Query("Select o FROM ApplicationOrder o WHERE o.status = ?1")
	Page<ApplicationOrder> findByStatus(ApplicationOrderStatus status, Pageable pageable);
}
