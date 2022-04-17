package com.dvd.service;

import com.dvd.DTO.ApplicationCustomerDTO;
import com.dvd.DTO.GetResourcesResponse;

/**
* Defines the service interface for the Customer resource.
*
* @author David Gheorghe
*/
public interface ApplicationCustomerService {
	/**
	 * Creates a new customer.
	 * 
	 * @param customerDTO - customer DTO object.
	 * @return The new customer.
	 */
	ApplicationCustomerDTO createCustomer(ApplicationCustomerDTO customerDTO);

	/** 
	 * Deletes a customer by id.
	 * @param id - the id of the customer that must be deleted.
	 * @return The DTO object of deleted customer.
	 */
	ApplicationCustomerDTO deleteCustomerById(Long id);

	/**
	 * Returns the customer. The response is based on pagination and sorting.
	 * Go to {@link com.dvd.utils.ApplicationConstants} class to see the parameters default values.
	 * 
	 * @param pageNo - page number.
	 * @param pageSize - page size.
	 * @param sortBy - criteria for sorting.
	 * @param sortDir - sorting direction  (ASC or DESC).
	 * @return A custom response with customer.
	 */
	GetResourcesResponse<ApplicationCustomerDTO> getAllCustomers(int pageNo, int pageSize, String sortBy, String sortDir);

	/**
	 * Returns a customer that matches the @param id.
	 * 
	 * @param id - the id of the customer that is retrieved.
	 * @return The retrieved customer.
	 */
	ApplicationCustomerDTO getCustomerById(Long id);

	/**
	 * Updates a customer by id.
	 * 
	 * @param id - the id of the updated customer.
	 * @param customerDTO - customer DTO object.
	 * @return The updated customer.
	 */
	ApplicationCustomerDTO updateCustomer(Long id, ApplicationCustomerDTO customerDTO);

	// TODO: add orders related methods
}