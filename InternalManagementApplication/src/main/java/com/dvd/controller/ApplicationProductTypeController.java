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

import com.dvd.DTO.ApplicationProductTypeDTO;
import com.dvd.DTO.GetResourcesResponse;
import com.dvd.service.ApplicationProductTypeService;
import com.dvd.utils.ApplicationConstants;

import lombok.RequiredArgsConstructor;

/**
* Defines the controller layer for the ProductType resource.
*
* @author David Gheorghe
*/
@RequiredArgsConstructor
@RequestMapping(ApplicationConstants.API_PRODUCT_TYPE_ROOT)
@RestController
public class ApplicationProductTypeController {
	private final ApplicationProductTypeService productTypeService;

	@PostMapping
	ResponseEntity<ApplicationProductTypeDTO> createProductType(@RequestBody ApplicationProductTypeDTO productTypeDTO) {
		ApplicationProductTypeDTO newProductType = productTypeService.createProductType(productTypeDTO);
		return new ResponseEntity<ApplicationProductTypeDTO>(newProductType, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}") 
	ResponseEntity<ApplicationProductTypeDTO> deleteById(@PathVariable Long id) {
		ApplicationProductTypeDTO deletedProductType = productTypeService.deleteById(id);
		return new ResponseEntity<ApplicationProductTypeDTO>(deletedProductType, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<GetResourcesResponse<ApplicationProductTypeDTO>> getProductTypes(
			@RequestParam(value = "pageNo", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = ApplicationConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = ApplicationConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
		return new ResponseEntity<GetResourcesResponse<ApplicationProductTypeDTO>>(productTypeService.getAllProductTypes(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);		
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApplicationProductTypeDTO> getProductTypeById(@PathVariable Long id) {
		ApplicationProductTypeDTO retrievedProductType = productTypeService.getProductTypeById(id);
		return new ResponseEntity<ApplicationProductTypeDTO>(retrievedProductType, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApplicationProductTypeDTO> updateProductType(@PathVariable Long id, @RequestBody ApplicationProductTypeDTO productTypeDTO) {
		ApplicationProductTypeDTO updatedProductType = productTypeService.updateProductType(id, productTypeDTO);
		return new ResponseEntity<ApplicationProductTypeDTO>(updatedProductType, HttpStatus.OK);
	}
}