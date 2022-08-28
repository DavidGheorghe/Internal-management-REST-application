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
	 * @return
	 */
	GetResourcesResponse<RetrievedOrderDTO> getAllOrdersFilteredBy(String keyword, int pageNo, int pageSize, String sortBy, String sortDir);
	
	
	/**
	 * Returns the orders which match a specific keyword and a status. The filtration is done on the customer name and company. 
	 * The response is based on pagination and sorting. Go to {@link com.dvd.utils.ApplicationConstants} class to see the parameters default values.
	 * 
	 * @param keyword	
	 * @param statusId
	 * @param pageNo
	 * @param pageSize
	 * @param sortBy
	 * @param sortDir
	 * @return
	 */
	GetResourcesResponse<RetrievedOrderDTO> getAllOrdersFilteredBy(String keyword, int statusId, int pageNo, int pageSize, String sortBy, String sortDir);
	
	/**
	 * @return a list with the pinned orders and the assigned orders.
	 */
	List<RetrievedOrderDTO> getDashboardOrders();
	
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
	 * Updates the due date of an order.
	 * 
	 * @param orderId	 - the id of the order being updated.
	 * @param newDueDate - the new due date.
	 * @return The updated order as DTO.
	 */
	RetrievedOrderDTO updateDueDate(Long orderId, String newDueDate);
	
	/**
	 * Returns the content of a specific order.
	 * 
	 * @param orderId - the id of the order.
	 * @return a list of DTOs with the content.
	 */
	List<RetrievedOrderContentDTO> getOrderContent(Long orderId);
	
	/**
	 * Pins an order to a specific user.
	 * 
	 * @param orderId	- the id of the pinned order.
	 * @param username	- the username of the user who pinned the order.
	 * @return a DTO with the pinned order.
	 */
	RetrievedOrderDTO pinOrder(Long orderId, String username);

	/**
	 * Unpins an order from a specific user.
	 * 
	 * @param orderId	- the id of the unpinned order.
	 * @param username	- the username of the user who unpinned the order.
	 * @return a DTO with the unpinned order.
	 */
	RetrievedOrderDTO unpinOrder(Long orderId, String username);
	
	/**
	 * Computes the final price of an item content. 
	 * 
	 * @param productId	- the id of the product being computed for.
	 * @param amount	- the amount of products.
	 * @return the final price of the item.
	 */
	Double computeContentItemPrice(Long productId, Integer amount);
	
	/**
	 * @return a DTO with the number of active orders and the number of orders which are due in a week.
	 */
	ActiveAndDueOrdersReportsDTO getActiveAndDueReports();
	
	/**
	 * @return a DTO with the number of completed orders in the last eight weeks.
	 */
	CompletedOrdersReportDTO getCompletedOrdersReport();

	/**
	 * @return a DTO with the number of new entered in the last eight weeks.
	 */
	NewOrdersReportDTO getNewOrdersReport();

	/**
	 * Assign an order to a specific user.
	 * 
	 * @param orderId	- the id of the order being assigned.
	 * @param username	- the asignee's username.
	 * @return 			the order assigned.
	 */
	RetrievedOrderDTO assignOrderToUser(Long orderId, String username);

	/**
	 * Removes an order from a specific user.
	 * 
	 * @param orderId	- the id of the order being removed.
	 * @param username	- the former asignee's username.
	 * @return the order assigned.
	 */
	RetrievedOrderDTO removeAssignedOrderFromUser(Long orderId, String username);
}
