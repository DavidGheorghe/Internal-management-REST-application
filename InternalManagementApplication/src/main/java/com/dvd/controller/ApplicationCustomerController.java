package com.dvd.controller;

import java.security.Principal;
import java.util.List;

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
import lombok.extern.log4j.Log4j2;

/**
* Defines the controller for the Customer resource.
*
* @author David Gheorghe
*/
@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping(ApplicationConstants.API_CUSTOMER_ROOT)
public class ApplicationCustomerController {
	private final ApplicationCustomerService customerService;
	
	@PostMapping()
	public ResponseEntity<ApplicationCustomerDTO> createCustomer(@RequestBody ApplicationCustomerDTO newCustomer, Principal principal) {
		ApplicationCustomerDTO createdCustomer = customerService.createCustomer(newCustomer);
		if (log.isInfoEnabled()) {
			log.info("User " + principal.getName() + " created new customer '" + createdCustomer.getFirstName() + " " + createdCustomer.getLastName() + "'.");
		}
		return new ResponseEntity<ApplicationCustomerDTO>(createdCustomer, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApplicationCustomerDTO> deleteCustomerById(@PathVariable Long id, Principal principal) {
		ApplicationCustomerDTO deletedCustomer = customerService.deleteCustomerById(id);
		if (log.isInfoEnabled()) {
			log.info("User " + principal.getName() + " deleted customer '" + deletedCustomer.getFirstName() +  " " + deletedCustomer.getLastName() + "'.");
		}
		return new ResponseEntity<ApplicationCustomerDTO>(deletedCustomer, HttpStatus.OK);
	}
	
	@GetMapping() 
	public ResponseEntity<GetResourcesResponse<ApplicationCustomerDTO>> getAllCustomers(
			@RequestParam(value = "pageNo", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = ApplicationConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = ApplicationConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
		GetResourcesResponse<ApplicationCustomerDTO> customers = customerService.getAllCustomers(pageNo, pageSize, sortBy, sortDir);
		return new ResponseEntity<GetResourcesResponse<ApplicationCustomerDTO>>(customers, HttpStatus.OK);		
	}

	@GetMapping("/all")
	public ResponseEntity<List<ApplicationCustomerDTO>> getAllCustomers() {
		List<ApplicationCustomerDTO> customers = customerService.getAllCustomers();
		return new ResponseEntity<List<ApplicationCustomerDTO>>(customers, HttpStatus.OK);
	}
	
	@GetMapping("/search") 
	public ResponseEntity<GetResourcesResponse<ApplicationCustomerDTO>> getAllCustomersFilteredBy(
			@RequestParam(value = "keyword") String keyword,
			@RequestParam(value = "pageNo", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = ApplicationConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = ApplicationConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
		GetResourcesResponse<ApplicationCustomerDTO> filteredCustomers = customerService.getAllCustomersFilteredBy(keyword, pageNo, pageSize, sortBy, sortDir);
		return new ResponseEntity<GetResourcesResponse<ApplicationCustomerDTO>>(filteredCustomers, HttpStatus.OK);		
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
