package com.dvd.controller;

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

import com.dvd.DTO.ApplicationCustomerDTO;
import com.dvd.DTO.GetResourcesResponse;
import com.dvd.service.ApplicationCustomerService;
import com.dvd.utils.ApplicationConstants;

import lombok.RequiredArgsConstructor;

/**
* Defines the controller for the Customer resource.
*
* @author David Gheorghe
*/
@RequiredArgsConstructor
@RestController
@RequestMapping(ApplicationConstants.API_CUSTOMER_ROOT)
public class ApplicationCustomerController {
	private final ApplicationCustomerService customerService;
	
	@PostMapping()
	public ResponseEntity<ApplicationCustomerDTO> createCustomer(@RequestBody ApplicationCustomerDTO newCustomer) {
		ApplicationCustomerDTO createdCustomer = customerService.createCustomer(newCustomer);
		return new ResponseEntity<ApplicationCustomerDTO>(createdCustomer, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApplicationCustomerDTO> deleteCustomerById(@PathVariable Long id) {
		ApplicationCustomerDTO deletedCustomer = customerService.deleteCustomerById(id);
		return new ResponseEntity<ApplicationCustomerDTO>(deletedCustomer, HttpStatus.OK);
	}
	
	@GetMapping() 
	public ResponseEntity<GetResourcesResponse<ApplicationCustomerDTO>> getAllCustomers(
			@RequestParam(value = "pageNo", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = ApplicationConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = ApplicationConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
		return new ResponseEntity<GetResourcesResponse<ApplicationCustomerDTO>>(customerService.getAllCustomers(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApplicationCustomerDTO> getCustomerById(@PathVariable Long id) {
		ApplicationCustomerDTO customer = customerService.getCustomerById(id);
		return new ResponseEntity<ApplicationCustomerDTO>(customer, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApplicationCustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody ApplicationCustomerDTO updatedCustomer) {
		ApplicationCustomerDTO customer = customerService.updateCustomer(id, updatedCustomer);
		return new ResponseEntity<ApplicationCustomerDTO>(customer, HttpStatus.OK);
	}
}
