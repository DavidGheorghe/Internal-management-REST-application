package com.dvd.service;

import java.util.List;

import com.dvd.DTO.GetResourcesResponse;
import com.dvd.DTO.order.ApplicationOrderDTO;
import com.dvd.DTO.order.RetrievedOrderContentDTO;
import com.dvd.DTO.order.RetrievedOrderDTO;
import com.dvd.DTO.reports.ActiveAndDueOrdersReportsDTO;
import com.dvd.DTO.reports.CompletedOrdersReportDTO;
import com.dvd.DTO.reports.NewOrdersReportDTO;

/**
* Defines the service layer for the Order resource.
*
* @author David Gheorghe
*/
public interface ApplicationOrderService {
	/**
	 * Creates a new order.
	 * 
	 * @param orderDTO - order DTO object.
	 * @return The new order as DTO.
	 */
	RetrievedOrderDTO createOrder(ApplicationOrderDTO orderDTO);
	
	/**
	 * Deletes an order by id.
	 * 
	 * @param id - the id of the order that must be deleted.
	 * @return The deleted order as DTO.
	 */
	RetrievedOrderDTO deleteById(Long id);
	
	/**
	 * Returns the orders. The response is based on pagination and sorting.
	 * Go to {@link com.dvd.utils.ApplicationConstants} class to see the parameters default values.
	 * 
	 * @param pageNo 	- page number.
	 * @param pageSize 	- page size.
	 * @param sortBy 	- criteria for sorting.
	 * @param sortDir 	- sorting direction  (ASC or DESC).
	 * @return A custom response with orders.
	 */
	GetResourcesResponse<RetrievedOrderDTO> getAllOrders(int pageNo, int pageSize, String sortBy, String sortDir);
	
	/**
	 * Returns the orders which match a specific keyword. The filtration is done on the customer name and company. 
	 * The response is based on pagination and sorting. Go to {@link com.dvd.utils.ApplicationConstants} class to see the parameters default values.
	 * 
	 * @param keyword 	- the string based on which the orders are filtered.
	 * @param pageNo 	- page number.
	 * @param pageSize 	- page size.
	 * @param sortBy 	- criteria for sorting.
	 * @param sortDir 	- sorting direction  (ASC or DESC).
	 * @return A custom response with orders.
	 */
	GetResourcesResponse<RetrievedOrderDTO> getAllOrdersFilteredBy(String keyword, int pageNo, int pageSize, String sortBy, String sortDir);
	GetResourcesResponse<RetrievedOrderDTO> getAllOrdersFilteredBy(String keyword, int statusId, int pageNo, int pageSize, String sortBy, String sortDir);
	
	List<RetrievedOrderDTO> getPinnedOrders();
	/**
	 * Retrieves an order by id.
	 * 
	 * @param id - the id of the order retrieved.
	 * @return The retrieved order as DTO.
	 */
	RetrievedOrderDTO getOrderById(Long id);
	
	RetrievedOrderDTO updateOrder(Long id, ApplicationOrderDTO orderDTO);
	
	/**
	 * Updates the status of an order.
	 * 
	 * @param orderId 	- the id of the order being updated.
	 * @param statusId 	- the id of the new status.
	 * @return The updated order as DTO.
	 */
	RetrievedOrderDTO updateOrderStatus(Long orderId, int statusId);
	
	/**
	 * Updates the customer of an order.
	 * 
	 * @param orderId 		- the id of the order being updated.
	 * @param customerId 	- the id of the new customer.
	 * @return The updated order as DTO.
	 */
//	RetrievedOrderDTO updateOrderCustomer(Long orderId, Long customerId);
	RetrievedOrderDTO updateDueDate(Long orderId, String newDueDate);
	
	List<RetrievedOrderContentDTO> getOrderContent(Long orderId);
	
	RetrievedOrderDTO pinOrder(Long id);
	RetrievedOrderDTO unpinOrder(Long id);
	
	Double computeContentPrice(Long productId, Integer amount);
	
	ActiveAndDueOrdersReportsDTO getOrdersReports();
	
	CompletedOrdersReportDTO getCompletedOrdersReport();

	NewOrdersReportDTO getNewOrdersReport();
}
