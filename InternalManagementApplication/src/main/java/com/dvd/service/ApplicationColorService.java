package com.dvd.service;

import java.util.List;

import com.dvd.DTO.ApplicationColorDTO;
import com.dvd.DTO.GetResourcesResponse;

/**
* Defines the Service layer for Color resource.
*
* @author David Gheorghe
*/
public interface ApplicationColorService {
	/**
	 * Creates a new color.
	 * 
	 * @param colorDTO - color DTO object.
	 * @return The new color.
	 */
	ApplicationColorDTO createColor(ApplicationColorDTO colorDTO);

	/**
	 * Deletes a color by id.
	 * 
	 * @param id - the id of the deleted color.
	 * @return The DTO object of the deleted color.
	 */
	ApplicationColorDTO deleteById(Long id);
	
	/**
	 * Returns the colors. The response is based on pagination and sorting.
	 * Go to {@link com.dvd.utils.ApplicationConstants} class to see the parameters default values.
	 *
	 * @param pageNo 	- page number.
	 * @param pageSize 	- page size.
	 * @param sortBy 	- criteria for sorting.
	 * @param sortDir 	- sorting direction  (ASC or DESC).
	 * @return A custom response with customer.
	 */
//	GetResourcesResponse<ApplicationColorDTO> getColors(int numberOfPigments, int pageNo, int pageSize, String sortBy, String sortDir);

	List<ApplicationColorDTO> getAllColors();

	/**
	 * Returns the colors which name contains the specified keyword. The response is based on pagination and sorting.
	 * Go to {@link com.dvd.utils.ApplicationConstants} class to see the parameters default values.
	 *
	 * @param pageNo 	- page number.
	 * @param pageSize 	- page size.
	 * @param sortBy 	- criteria for sorting.
	 * @param sortDir 	- sorting direction  (ASC or DESC).
	 * @return A custom response with customer.
	 */
	GetResourcesResponse<ApplicationColorDTO> getColorsFilteredBy(String keyword, int pageNo, int pageSize, String sortBy, String sortDir);
	
	GetResourcesResponse<ApplicationColorDTO> getColorsWithOnePigmentFilteredBy(String keyword, int pageNo, int pageSize, String sortBy, String sortDir);	
	GetResourcesResponse<ApplicationColorDTO> getColorsWithTwoPigmentsFilteredBy(String keyword, int pageNo, int pageSize, String sortBy, String sortDir);	
	GetResourcesResponse<ApplicationColorDTO> getColorsWithThreePigmentsFilteredBy(String keyword, int pageNo, int pageSize, String sortBy, String sortDir);	
	
	/**
	 * Return a color by id.
	 * 
	 * @param id - the id of the color that is retrieved.
	 * @return The DTO of the retrieved color.
	 */
	ApplicationColorDTO getColorById(Long id);

	/**
	 * Updates a color.
	 * 
	 * @param id 		- the id of the updated color.
	 * @param colorDTO 	- color DTO object.
	 * @return The DTO of the updated color
	 */
	ApplicationColorDTO updateColor(Long id, ApplicationColorDTO colorDTO);

	// TODO add order, formula calculator related methods
}