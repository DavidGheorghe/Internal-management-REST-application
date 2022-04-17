package com.dvd.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dvd.DTO.ApplicationCustomerDTO;
import com.dvd.DTO.GetResourcesResponse;
import com.dvd.entity.ApplicationCustomer;
import com.dvd.exception.CustomerCredentialTakenException;
import com.dvd.exception.ResourceNotFoundException;
import com.dvd.repository.ApplicationCustomerRepository;
import com.dvd.service.ApplicationCustomerService;

import lombok.RequiredArgsConstructor;

/**
* Defines the implementation of the service layer for Customer resource.
*
* @author David Gheorghe
*/
@RequiredArgsConstructor
@Service
public class ApplicationCustomerServiceImpl implements ApplicationCustomerService {

	private final ApplicationCustomerRepository customerRepository;
	private final ModelMapper mapper;

	@Override
	public ApplicationCustomerDTO createCustomer(ApplicationCustomerDTO customerDTO) {
		validateNewCustomer(customerDTO);
		ApplicationCustomer newCustomer = mapper.map(customerDTO, ApplicationCustomer.class);
		ApplicationCustomer createdCustomer = customerRepository.save(newCustomer);
		return mapper.map(createdCustomer, ApplicationCustomerDTO.class);
	}

	@Override
	public ApplicationCustomerDTO deleteCustomerById(Long id) {
		ApplicationCustomer customer = getCustomerByIdOrElseThrow(id);
		ApplicationCustomerDTO deletedCustomer = mapper.map(customer, ApplicationCustomerDTO.class);
		customerRepository.deleteById(id);
		return deletedCustomer;
	}

	@Override
	public GetResourcesResponse<ApplicationCustomerDTO> getAllCustomers(int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<ApplicationCustomer> customers = customerRepository.findAll(pageable);
		List<ApplicationCustomer> listOfCustomers = customers.getContent();
		List<ApplicationCustomerDTO> content = listOfCustomers
													.stream()
													.map(customer -> mapper.map(customer, ApplicationCustomerDTO.class))
													.collect(Collectors.toList());
		GetResourcesResponse<ApplicationCustomerDTO> response = new GetResourcesResponse<>();
		response.setGetResourcesResponseFields(content, customers);
		return response;
	}

	@Override
	public ApplicationCustomerDTO getCustomerById(Long id) {
		ApplicationCustomer customer = getCustomerByIdOrElseThrow(id);
		return mapper.map(customer, ApplicationCustomerDTO.class);
	}

	@Override
	public ApplicationCustomerDTO updateCustomer(Long id, ApplicationCustomerDTO customerDTO) {
		ApplicationCustomer customer = getCustomerByIdOrElseThrow(id);

		updateField(customer, 1, customerDTO.getFirstName(), customer.getFirstName());
		updateField(customer, 2, customerDTO.getLastName(), customer.getLastName());
		updateField(customer, 3, customerDTO.getEmail(), customer.getEmail());
		updateField(customer, 4, customerDTO.getPhoneNumber(), customer.getPhoneNumber());
		updateField(customer, 5, customerDTO.getCompanyName(), customer.getCompanyName());
		updateField(customer, 6, customerDTO.getCui(), customer.getCui());
		updateField(customer, 7, customerDTO.getBillingAddress(), customer.getBillingAddress());
		updateField(customer, 8, customerDTO.getDeliveryAddress(), customer.getDeliveryAddress());

		customerRepository.save(customer);
		return mapper.map(customer, ApplicationCustomerDTO.class);
	}

	/**
	 * Updates a certain field.
	 * 
	 * @param customer - the customer that is updated
	 * @param fieldNo - the order from the DTO object of the field.
	 * @param newValue - the new value.
	 * @param oldValue - the old value.
	 */
	private void updateField(ApplicationCustomer customer, int fieldNo, String newValue, String oldValue) {
		switch (fieldNo) {
			case 1:
				if (isFieldValidForUpdate(newValue, oldValue)) {
					customer.setFirstName(newValue);
				}
				break;
			case 2:
				if (isFieldValidForUpdate(newValue, oldValue)) {
					customer.setLastName(newValue);
				}
				break;
			case 3:
				if (isFieldValidForUpdate(newValue, oldValue)) {
					customer.setEmail(newValue);
				}
				break;
			case 4:
				if (isFieldValidForUpdate(newValue, oldValue)) {
					customer.setPhoneNumber(newValue);
				}
				break;
			case 5:
				if (isFieldValidForUpdate(newValue, oldValue)) {
					customer.setCompanyName(newValue);
				}
				break;
			case 6:
				if (isFieldValidForUpdate(newValue, oldValue)) {
					customer.setCui(newValue);
				}
				break;
			case 7:
				if (isFieldValidForUpdate(newValue, oldValue)) {
					customer.setBillingAddress(newValue);
				}
				break;
			case 8:
				if (isFieldValidForUpdate(newValue, oldValue)) {
					customer.setDeliveryAddress(newValue);
				}
				break;
		}
	}

	/**
	 * Checks if the new value is not null, nor blank, nor same as the old value.
	 * 
	 * @param newValue - the new value.
	 * @param oldValue - the old value.
	 * @return true if the field is valid, false otherwise.
	 */
	private boolean isFieldValidForUpdate(String newValue, String oldValue) {
		boolean isValid = false;
		if (newValue != null && !newValue.isBlank() && !newValue.equals(oldValue)) {
			isValid = true;
		}
		return isValid;
	}

	/**
	 * Validates if a new customer is valid: if the credentials provided are not taken for the unique columns.
	 * 
	 * @param newCustomer - the DTO object of a new customer.
	 */
	private void validateNewCustomer(ApplicationCustomerDTO newCustomer) {
		String billingAddress = newCustomer.getBillingAddress();
		String cui = newCustomer.getCui();
		String companyName = newCustomer.getCompanyName();
		String email = newCustomer.getEmail();
		String phoneNumber = newCustomer.getPhoneNumber();
		String deliveryAddress = newCustomer.getDeliveryAddress();

		if (customerRepository.existsByBillingAddress(billingAddress)) {
			throw new CustomerCredentialTakenException("Billing Address", billingAddress);
		} else if (customerRepository.existsByCui(cui)) {
			throw new CustomerCredentialTakenException("C.U.I.", cui);
		} else if (customerRepository.existsByDeliveryAddress(deliveryAddress)) {
			throw new CustomerCredentialTakenException("Delivery Address", deliveryAddress);
		} else if (customerRepository.existsByEmail(email)) {
			throw new CustomerCredentialTakenException("Email", email);
		} else if (customerRepository.existsByPhoneNumber(phoneNumber)) {
			throw new CustomerCredentialTakenException("Phone Number", phoneNumber);
		} else if (customerRepository.existsByCompanyName(companyName)) {
			throw new CustomerCredentialTakenException("Company Name", companyName);
		}
	}

	/**
	 * Returns a customer by id or throw an exception if it is not found.
	 * 
	 * @param id - the id of the customer that must be retrieved.
	 * @return The customer with id @param id.
	 */
	private ApplicationCustomer getCustomerByIdOrElseThrow(Long id) {
		return customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", String.valueOf(id)));
	}
}