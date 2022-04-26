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

import com.dvd.DTO.ApplicationProductDTO;
import com.dvd.DTO.GetResourcesResponse;
import com.dvd.DTO.RetrievedApplicationProductDTO;
import com.dvd.service.ApplicationProductService;
import com.dvd.utils.ApplicationConstants;

import lombok.RequiredArgsConstructor;

/**
* Defines the controller layer for the Product resource.
*
* @author David Gheorghe
*/
@RequiredArgsConstructor
@RequestMapping(ApplicationConstants.API_PRODUCT_ROOT)
@RestController
public class ApplicationProductController {
	private final ApplicationProductService productService;
	
	@PostMapping
	public ResponseEntity<RetrievedApplicationProductDTO> createProduct(@RequestBody ApplicationProductDTO productDTO) {
		RetrievedApplicationProductDTO createdProduct = productService.createProduct(productDTO);
		return new ResponseEntity<RetrievedApplicationProductDTO>(createdProduct, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<RetrievedApplicationProductDTO> deleteById(@PathVariable Long id) {
		RetrievedApplicationProductDTO deletedProduct = productService.deleteById(id);
		return new ResponseEntity<RetrievedApplicationProductDTO>(deletedProduct, HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<GetResourcesResponse<RetrievedApplicationProductDTO>> getAllProducts(
			@RequestParam(value = "pageNo", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = ApplicationConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = ApplicationConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
		return new ResponseEntity<GetResourcesResponse<RetrievedApplicationProductDTO>>(productService.getAllProducts(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RetrievedApplicationProductDTO> getProductById(@PathVariable Long id) {
		RetrievedApplicationProductDTO retrievedProduct = productService.getProductById(id);
		return new ResponseEntity<RetrievedApplicationProductDTO>(retrievedProduct, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<RetrievedApplicationProductDTO> updateProduct(@PathVariable Long id, @RequestBody ApplicationProductDTO productDTO) {
		RetrievedApplicationProductDTO updatedProduct = productService.updateProduct(id, productDTO);
		return new ResponseEntity<RetrievedApplicationProductDTO>(updatedProduct, HttpStatus.OK);
	}
}
