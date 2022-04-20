package com.dvd.service;

import com.dvd.DTO.ApplicationProductTypeDTO;
import com.dvd.DTO.GetResourcesResponse;

/**
* Defines the service layer for the ProductType resource.
*
* @author David Gheorghe
*/
public interface ApplicationProductTypeService {
	/**
	 * Creates a new type for products.
	 * 
	 * @param productTypeDTO - DTO object for ProductType.
	 * @return The new type.
	 */
	ApplicationProductTypeDTO createProductType(ApplicationProductTypeDTO productTypeDTO);

	/**
	 * Deletes a product type by id.
	 * 
	 * @param id - the id of the deleted type.
	 * @return The DTO object of the deleted type.
	 */
	ApplicationProductTypeDTO deleteById(Long id);

	/**
	 * Returns the types for all products. The response is based on pagination and sorting.
	 * Go to {@link com.dvd.utils.ApplicationConstants} class to see the parameters default values.
	 *
	 * @param pageNo - page number.
	 * @param pageSize - page size.
	 * @param sortBy - criteria for sorting.
	 * @param sortDir - sorting direction  (ASC or DESC).
	 * @return A custom response with product types.
	 */
	GetResourcesResponse<ApplicationProductTypeDTO> getAllProductTypes(int pageNo, int pageSize, String sortBy, String sortDir);

	/**
	 * Return a product type by id.
	 * 
	 * @param id - the id of the product type that is retrieved.
	 * @return The DTO of the retrieved product type.
	 */
	ApplicationProductTypeDTO getProductTypeById(Long id);

	/**
	 * Updates a product type.
	 * 
	 * @param id - the id of the type being updated.
	 * @param productTypeDTO - product type DTO object.
	 * @return The DTO of the updated type.
	 */
	ApplicationProductTypeDTO updateProductType(Long id, ApplicationProductTypeDTO productTypeDTO);
}