package com.dvd.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dvd.DTO.GetResourcesResponse;
import com.dvd.DTO.order.ApplicationOrderDTO;
import com.dvd.DTO.order.RetrievedOrderDTO;
import com.dvd.service.ApplicationOrderService;
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
	
	@PutMapping("/{id}")
	public ResponseEntity<RetrievedOrderDTO> updateOrderStatus(@PathVariable Long id, @RequestBody ApplicationOrderDTO orderDTO) {
		RetrievedOrderDTO updatedOrderDTO = orderService.updateOrder(id, orderDTO);
		return new ResponseEntity<RetrievedOrderDTO>(updatedOrderDTO, HttpStatus.OK);
	}
//	
//	@PutMapping("/customer/{orderId}/{customerId}")
//	public ResponseEntity<RetrievedOrderDTO> updateOrderCustomer(@PathVariable Long orderId, @PathVariable Long customerId) {
//		RetrievedOrderDTO updatedOrderDTO = orderService.updateOrderCustomer(orderId, customerId);
//		return new ResponseEntity<RetrievedOrderDTO>(updatedOrderDTO, HttpStatus.OK);
//	}
}