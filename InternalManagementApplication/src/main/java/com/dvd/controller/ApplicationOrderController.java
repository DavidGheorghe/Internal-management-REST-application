package com.dvd.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dvd.DTO.ApplicationUserDTO;
import com.dvd.DTO.GetResourcesResponse;
import com.dvd.DTO.order.ApplicationOrderDTO;
import com.dvd.DTO.order.RetrievedOrderContentDTO;
import com.dvd.DTO.order.RetrievedOrderDTO;
import com.dvd.DTO.reports.ActiveAndDueOrdersReportsDTO;
import com.dvd.DTO.reports.CompletedOrdersReportDTO;
import com.dvd.DTO.reports.NewOrdersReportDTO;
import com.dvd.service.ApplicationOrderService;
import com.dvd.service.ApplicationUserService;
import com.dvd.utils.ApplicationConstants;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
* Defines the controller layer for the Order resource.
*
* @author David Gheorghe
*/
@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping(ApplicationConstants.API_ORDER_ROOT)
public class ApplicationOrderController {
	private final ApplicationOrderService orderService;
	private final ApplicationUserService userService;
	
	@PostMapping
	public ResponseEntity<RetrievedOrderDTO> createOrder(@RequestBody ApplicationOrderDTO orderDTO, Principal principal) {
		RetrievedOrderDTO createdOrderDTO = orderService.createOrder(orderDTO);
		if (log.isInfoEnabled()) {
			log.info("User " + principal.getName() + " added new order with id = " + createdOrderDTO.getId() + ", for the customer: " + createdOrderDTO.getCustomer() + ".");
		}
		return new ResponseEntity<RetrievedOrderDTO>(createdOrderDTO, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RetrievedOrderDTO> getOrderById(@PathVariable Long id) {
		RetrievedOrderDTO retrievedOrderDTO = orderService.getOrderById(id);
		return new ResponseEntity<RetrievedOrderDTO>(retrievedOrderDTO, HttpStatus.OK);
	}
	@GetMapping("/content/{id}")
	public ResponseEntity<List<RetrievedOrderContentDTO>> getOrderContent(@PathVariable Long id) {
		List<RetrievedOrderContentDTO> content = orderService.getOrderContent(id);
		return new ResponseEntity<List<RetrievedOrderContentDTO>>(content, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<RetrievedOrderDTO> deleteById(@PathVariable Long id) {
		RetrievedOrderDTO deletedOrderDTO = orderService.deleteById(id);
		return new ResponseEntity<RetrievedOrderDTO>(deletedOrderDTO, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<GetResourcesResponse<RetrievedOrderDTO>> getAllOrders(
			@RequestParam(value = "pageNo", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = ApplicationConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = ApplicationConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
		GetResourcesResponse<RetrievedOrderDTO> orders = orderService.getAllOrders(pageNo, pageSize, sortBy, sortDir);
		return new ResponseEntity<GetResourcesResponse<RetrievedOrderDTO>>(orders, HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<GetResourcesResponse<RetrievedOrderDTO>> getAllOrdersFilteredBy(
			@RequestParam(value = "keyword") String keyword,
			@RequestParam(value = "pageNo", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = ApplicationConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = ApplicationConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
		GetResourcesResponse<RetrievedOrderDTO> orders = orderService.getAllOrdersFilteredBy(keyword, pageNo, pageSize, sortBy, sortDir);
		return new ResponseEntity<GetResourcesResponse<RetrievedOrderDTO>>(orders, HttpStatus.OK);
	}

	@GetMapping("/status")
	public ResponseEntity<GetResourcesResponse<RetrievedOrderDTO>> getAllOrdersFilteredByStatus(
			@RequestParam(value = "keyword") String keyword,
			@RequestParam(value = "statusId") int statusId,
			@RequestParam(value = "pageNo", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = ApplicationConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = ApplicationConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
		GetResourcesResponse<RetrievedOrderDTO> orders = orderService.getAllOrdersFilteredBy(keyword, statusId, pageNo, pageSize, sortBy, sortDir);
		return new ResponseEntity<GetResourcesResponse<RetrievedOrderDTO>>(orders, HttpStatus.OK);
	}
	
	@GetMapping("/dashboard")
	public ResponseEntity<List<RetrievedOrderDTO>> getDashboardOrders() {
		List<RetrievedOrderDTO> pinnedOrders = orderService.getDashboardOrders();
		return new ResponseEntity<List<RetrievedOrderDTO>>(pinnedOrders, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<RetrievedOrderDTO> updateOrder(@PathVariable Long id, @RequestBody ApplicationOrderDTO orderDTO) {
		RetrievedOrderDTO updatedOrderDTO = orderService.updateOrder(id, orderDTO);
		return new ResponseEntity<RetrievedOrderDTO>(updatedOrderDTO, HttpStatus.OK);
	}

	@PutMapping("/status/{orderId}/{statusId}")
	public ResponseEntity<RetrievedOrderDTO> updateOrderStatus(@PathVariable Long orderId, @PathVariable int statusId) {
		RetrievedOrderDTO updatedOrderDTO = orderService.updateOrderStatus(orderId, statusId);
		return new ResponseEntity<RetrievedOrderDTO>(updatedOrderDTO, HttpStatus.OK);
	}
	
	@PutMapping("/pin/{orderId}/{username}")
	public ResponseEntity<RetrievedOrderDTO> pinOrder(@PathVariable Long orderId, @PathVariable String username) {
		RetrievedOrderDTO orderDTO = orderService.pinOrder(orderId, username);
		return new ResponseEntity<RetrievedOrderDTO>(orderDTO, HttpStatus.OK);
	}

	@PutMapping("/unpin/{orderId}/{username}")
	public ResponseEntity<RetrievedOrderDTO> unpinOrder(@PathVariable Long orderId, @PathVariable String username) {
		RetrievedOrderDTO orderDTO = orderService.unpinOrder(orderId, username);
		return new ResponseEntity<RetrievedOrderDTO>(orderDTO, HttpStatus.OK);
	}
	
	@PutMapping("/assign/{orderId}/{username}")
	public ResponseEntity<RetrievedOrderDTO> assignOrderToUser(@PathVariable Long orderId, @PathVariable String username) {
		RetrievedOrderDTO orderDTO = orderService.assignOrderToUser(orderId, username);
		return new ResponseEntity<RetrievedOrderDTO>(orderDTO, HttpStatus.OK);
	}
	
	@PutMapping("/remove-assigned/{orderId}/{username}")
	public ResponseEntity<RetrievedOrderDTO> removeAssignedOrderFromUser(@PathVariable Long orderId, @PathVariable String username) {
		RetrievedOrderDTO orderDTO = orderService.removeAssignedOrderFromUser(orderId, username);
		return new ResponseEntity<RetrievedOrderDTO>(orderDTO, HttpStatus.OK);
	}
	
	@PutMapping("/due-date")
	public ResponseEntity<RetrievedOrderDTO> updateDueDate(@RequestParam(value = "id") Long orderId, @RequestParam(value = "due-date") String dueDate) {
		RetrievedOrderDTO orderDTO = orderService.updateDueDate(orderId, dueDate);
		return new ResponseEntity<RetrievedOrderDTO>(orderDTO, HttpStatus.OK);
	}
	
	@GetMapping("/content-price")
	public ResponseEntity<Double> computeContentPrice(@RequestParam(value = "productId") Long priceId, @RequestParam(value = "amount") Integer amount) {
		Double price = orderService.computeContentItemPrice(priceId, amount);
		return new ResponseEntity<Double>(price, HttpStatus.OK);
	}
	
	@GetMapping("/reports")
	public ResponseEntity<ActiveAndDueOrdersReportsDTO> getOrdersReports() {
		ActiveAndDueOrdersReportsDTO ordersReports = orderService.getActiveAndDueReports();
		return new ResponseEntity<ActiveAndDueOrdersReportsDTO>(ordersReports, HttpStatus.OK);
	}
	
	@GetMapping("/reports/complete")
	public ResponseEntity<CompletedOrdersReportDTO> getCompletedOrdersReport() {
		CompletedOrdersReportDTO ordersReport = orderService.getCompletedOrdersReport();
		return new ResponseEntity<CompletedOrdersReportDTO>(ordersReport, HttpStatus.OK);
	}
	
	@GetMapping("/reports/new")
	public ResponseEntity<NewOrdersReportDTO> getNewOrdersReport() {
		NewOrdersReportDTO ordersReport = orderService.getNewOrdersReport();
		return new ResponseEntity<NewOrdersReportDTO>(ordersReport, HttpStatus.OK);
	}
}