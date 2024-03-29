package com.dvd.controller;

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

import com.dvd.DTO.ApplicationColorDTO;
import com.dvd.DTO.GetResourcesResponse;
import com.dvd.service.ApplicationColorService;
import com.dvd.utils.ApplicationConstants;

import lombok.RequiredArgsConstructor;

/**
 * Defines the controller layer for Color resource.
 *
 * @author David Gheorghe
 */
@RequiredArgsConstructor
@RequestMapping(ApplicationConstants.API_COLOR_ROOT)
@RestController
public class ApplicationColorController {
	private final ApplicationColorService colorService;

	@PostMapping
	public ResponseEntity<ApplicationColorDTO> createColor(@RequestBody ApplicationColorDTO colorDTO) {
		ApplicationColorDTO createdColor = colorService.createColor(colorDTO);
		return new ResponseEntity<ApplicationColorDTO>(createdColor, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApplicationColorDTO> deleteById(@PathVariable Long id) {
		ApplicationColorDTO deletedColor = colorService.deleteById(id);
		return new ResponseEntity<ApplicationColorDTO>(deletedColor, HttpStatus.OK);
	}

//	@GetMapping
//	public ResponseEntity<GetResourcesResponse<ApplicationColorDTO>> getColors(
//			@RequestParam(value = "numberOfPigments", defaultValue = "3", required = false) int numberOfPigments,
//			@RequestParam(value = "pageNo", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
//			@RequestParam(value = "pageSize", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
//			@RequestParam(value = "sortBy", defaultValue = ApplicationConstants.DEFAULT_SORT_BY, required = false) String sortBy,
//			@RequestParam(value = "sortDir", defaultValue = ApplicationConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
//		GetResourcesResponse<ApplicationColorDTO> colors = colorService.getColors(numberOfPigments, pageNo, pageSize, sortBy, sortDir);
//		return new ResponseEntity<GetResourcesResponse<ApplicationColorDTO>>(colors, HttpStatus.OK);		
//	}

	@GetMapping("/all")
	public ResponseEntity<List<ApplicationColorDTO>> getAllColors() {
		List<ApplicationColorDTO> colors = colorService.getAllColors();
		return new ResponseEntity<List<ApplicationColorDTO>>(colors, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApplicationColorDTO> getColorById(@PathVariable Long id) {
		ApplicationColorDTO retreivedColor = colorService.getColorById(id);
		return new ResponseEntity<ApplicationColorDTO>(retreivedColor, HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<GetResourcesResponse<ApplicationColorDTO>> getColorsFilteredBy(
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "pageNo", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = ApplicationConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = ApplicationConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
		GetResourcesResponse<ApplicationColorDTO> filteredColors = colorService.getColorsFilteredBy(keyword, pageNo, pageSize, sortBy, sortDir);
		return new ResponseEntity<GetResourcesResponse<ApplicationColorDTO>>(filteredColors, HttpStatus.OK);
	} 
	
	@GetMapping("/one-pigment")
	public ResponseEntity<GetResourcesResponse<ApplicationColorDTO>> getColorsWithOnePigment(
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "pageNo", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = ApplicationConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = ApplicationConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
		GetResourcesResponse<ApplicationColorDTO> colors = colorService.getColorsWithOnePigmentFilteredBy(keyword, pageNo, pageSize, sortBy, sortDir);
		return new ResponseEntity<GetResourcesResponse<ApplicationColorDTO>>(colors, HttpStatus.OK);		
	}
	
	@GetMapping("/two-pigments")
	public ResponseEntity<GetResourcesResponse<ApplicationColorDTO>> getColorsWithTwoPigments(
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "pageNo", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = ApplicationConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = ApplicationConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
		GetResourcesResponse<ApplicationColorDTO> colors = colorService.getColorsWithTwoPigmentsFilteredBy(keyword, pageNo, pageSize, sortBy, sortDir);
		return new ResponseEntity<GetResourcesResponse<ApplicationColorDTO>>(colors, HttpStatus.OK);		
	}
	
	@GetMapping("/three-pigments")
	public ResponseEntity<GetResourcesResponse<ApplicationColorDTO>> getColorsWithThreePigments(
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "pageNo", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = ApplicationConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = ApplicationConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
		GetResourcesResponse<ApplicationColorDTO> colors = colorService.getColorsWithThreePigmentsFilteredBy(keyword, pageNo, pageSize, sortBy, sortDir);
		return new ResponseEntity<GetResourcesResponse<ApplicationColorDTO>>(colors, HttpStatus.OK);		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApplicationColorDTO> updateColor(@PathVariable Long id, @RequestBody ApplicationColorDTO colorDTO) {
		ApplicationColorDTO updatedColor = colorService.updateColor(id, colorDTO);
		return new ResponseEntity<ApplicationColorDTO>(updatedColor, HttpStatus.OK);
	}
}