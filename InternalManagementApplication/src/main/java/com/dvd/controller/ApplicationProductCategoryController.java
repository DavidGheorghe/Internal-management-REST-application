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

import com.dvd.DTO.ApplicationProductCategoryDTO;
import com.dvd.DTO.GetResourcesResponse;
import com.dvd.service.ApplicationProductCategoryService;
import com.dvd.utils.ApplicationConstants;

import lombok.RequiredArgsConstructor;

/**
* Defines the controller layer for the ProductCategory resource.
*
* @author David Gheorghe
*/
@RequiredArgsConstructor
@RequestMapping(ApplicationConstants.API_PRODUCT_CATEGORY_ROOT)
@RestController
public class ApplicationProductCategoryController {
	private final ApplicationProductCategoryService productCategoryService;

	@PostMapping
	ResponseEntity<ApplicationProductCategoryDTO> createProductCategory(@RequestBody ApplicationProductCategoryDTO productCategoryDTO) {
		ApplicationProductCategoryDTO newProductCategory = productCategoryService.createProductCategory(productCategoryDTO);
		return new ResponseEntity<ApplicationProductCategoryDTO>(newProductCategory, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}") 
	ResponseEntity<ApplicationProductCategoryDTO> deleteById(@PathVariable Long id) {
		ApplicationProductCategoryDTO deletedProductCategory = productCategoryService.deleteById(id);
		return new ResponseEntity<ApplicationProductCategoryDTO>(deletedProductCategory, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<GetResourcesResponse<ApplicationProductCategoryDTO>> getProductCategories(
			@RequestParam(value = "pageNo", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = ApplicationConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = ApplicationConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
		return new ResponseEntity<GetResourcesResponse<ApplicationProductCategoryDTO>>(productCategoryService.getAllProductCategories(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);		
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApplicationProductCategoryDTO> getProductCategoryById(@PathVariable Long id) {
		ApplicationProductCategoryDTO retrievedProductCategory = productCategoryService.getProductCategoryById(id);
		return new ResponseEntity<ApplicationProductCategoryDTO>(retrievedProductCategory, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApplicationProductCategoryDTO> updateProductCategory(@PathVariable Long id, @RequestBody ApplicationProductCategoryDTO productCategoryDTO) {
		ApplicationProductCategoryDTO updatedProductCategory = productCategoryService.updateProductCategory(id, productCategoryDTO);
		return new ResponseEntity<ApplicationProductCategoryDTO>(updatedProductCategory, HttpStatus.OK);
	}
}