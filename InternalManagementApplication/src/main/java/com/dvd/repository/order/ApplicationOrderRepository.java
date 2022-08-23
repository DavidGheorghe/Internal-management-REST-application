package com.dvd.repository.order;

import java.util.List;

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
	
	@Query(value = "SELECT count(*) FROM internalmanagement.orders o where o.due_date between now() and date_add(now(), interval 7 day)", 
			nativeQuery = true)
	Integer getNumberOfDueOrdersInOneWeek();
	
	@Query(value = "SELECT count(*) FROM internalmanagement.orders o where o.status <> 'COMPLETE' and o.status <> 'NEW'",
			nativeQuery = true)
	Integer getNumberOfActiveOrders();
	
	@Query(value = "SELECT count(*) FROM internalmanagement.orders o where o.completion_date between date_add(now(), interval -56 day) "
			+ "and date_add(now(), interval -50 day) ",
			nativeQuery = true)
	Integer getNumberOfCompletedOrdersEightWeeks();
	
	@Query(value = "SELECT count(*) FROM internalmanagement.orders o where o.completion_date between date_add(now(), interval -49 day) "
			+ "and date_add(now(), interval -43 day) ",
			nativeQuery = true)
	Integer getNumberOfCompletedOrdersSevenWeeks();
	
	@Query(value = "SELECT count(*) FROM internalmanagement.orders o where o.completion_date between date_add(now(), interval -42 day) "
			+ "and date_add(now(), interval -36 day) ",
			nativeQuery = true)
	Integer getNumberOfCompletedOrdersSixWeeks();
	
	@Query(value = "SELECT count(*) FROM internalmanagement.orders o where o.completion_date between date_add(now(), interval -35 day) "
			+ "and date_add(now(), interval -29 day) ",
			nativeQuery = true)
	Integer getNumberOfCompletedOrdersFiveWeeks();
	
	@Query(value = "SELECT count(*) FROM internalmanagement.orders o where o.completion_date between date_add(now(), interval -28 day) "
			+ "and date_add(now(), interval -22 day) ",
			nativeQuery = true)
	Integer getNumberOfCompletedOrdersFourWeeks();
	
	@Query(value = "SELECT count(*) FROM internalmanagement.orders o where o.completion_date between date_add(now(), interval -21 day) "
			+ "and date_add(now(), interval -15 day) ",
			nativeQuery = true)
	Integer getNumberOfCompletedOrdersThreeWeeks();
	
	@Query(value = "SELECT count(*) FROM internalmanagement.orders o where o.completion_date between date_add(now(), interval -14 day) "
			+ "and date_add(now(), interval -8 day) ",
			nativeQuery = true)
	Integer getNumberOfCompletedOrdersTwoWeeks();
	
	@Query(value = "SELECT count(*) FROM internalmanagement.orders o where o.completion_date between date_add(now(), interval -7 day) "
			+ "and now() ",
			nativeQuery = true)
	Integer getNumberOfCompletedOrdersOneWeek();
	
	@Query(value = "SELECT count(*) FROM internalmanagement.orders o where o.entry_date between date_add(now(), interval -56 day) "
			+ "and date_add(now(), interval -50 day) ",
			nativeQuery = true)
	Integer getNumberOfNewOrdersEightWeeks();
	
	@Query(value = "SELECT count(*) FROM internalmanagement.orders o where o.entry_date between date_add(now(), interval -49 day) "
			+ "and date_add(now(), interval -43 day) ",
			nativeQuery = true)
	Integer getNumberOfNewOrdersSevenWeeks();
	
	@Query(value = "SELECT count(*) FROM internalmanagement.orders o where o.entry_date between date_add(now(), interval -42 day) "
			+ "and date_add(now(), interval -36 day) ",
			nativeQuery = true)
	Integer getNumberOfNewOrdersSixWeeks();
	
	@Query(value = "SELECT count(*) FROM internalmanagement.orders o where o.entry_date between date_add(now(), interval -35 day) "
			+ "and date_add(now(), interval -29 day) ",
			nativeQuery = true)
	Integer getNumberOfNewOrdersFiveWeeks();
	
	@Query(value = "SELECT count(*) FROM internalmanagement.orders o where o.entry_date between date_add(now(), interval -28 day) "
			+ "and date_add(now(), interval -22 day) ",
			nativeQuery = true)
	Integer getNumberOfNewOrdersFourWeeks();
	
	@Query(value = "SELECT count(*) FROM internalmanagement.orders o where o.entry_date between date_add(now(), interval -21 day) "
			+ "and date_add(now(), interval -15 day) ",
			nativeQuery = true)
	Integer getNumberOfNewOrdersThreeWeeks();
	
	@Query(value = "SELECT count(*) FROM internalmanagement.orders o where o.entry_date between date_add(now(), interval -14 day) "
			+ "and date_add(now(), interval -8 day) ",
			nativeQuery = true)
	Integer getNumberOfNewOrdersTwoWeeks();
	
	@Query(value = "SELECT count(*) FROM internalmanagement.orders o where o.entry_date between date_add(now(), interval -7 day) "
			+ "and now() ",
			nativeQuery = true)
	Integer getNumberOfNewOrdersOneWeek();
	
	
}
