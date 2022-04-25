package com.dvd.service;

import com.dvd.DTO.ApplicationProductCategoryDTO;
import com.dvd.DTO.GetResourcesResponse;

/**
* Defines the service layer for the ProductCategory resource.
*
* @author David Gheorghe
*/
public interface ApplicationProductCategoryService {
	/**
	 * Creates a new category for products.
	 * 
	 * @param productCategoryDTO - DTO object for ProductCategory.
	 * @return The new type.
	 */
	ApplicationProductCategoryDTO createProductCategory(ApplicationProductCategoryDTO productCategoryDTO);

	/**
	 * Deletes a product category by id.
	 * 
	 * @param id - the id of the deleted category.
	 * @return The DTO object of the deleted category.
	 */
	ApplicationProductCategoryDTO deleteById(Long id);

	/**
	 * Returns the categories for all products. The response is based on pagination and sorting.
	 * Go to {@link com.dvd.utils.ApplicationConstants} class to see the parameters default values.
	 *
	 * @param pageNo - page number.
	 * @param pageSize - page size.
	 * @param sortBy - criteria for sorting.
	 * @param sortDir - sorting direction  (ASC or DESC).
	 * @return A custom response with product categories.
	 */
	GetResourcesResponse<ApplicationProductCategoryDTO> getAllProductCategories(int pageNo, int pageSize, String sortBy, String sortDir);

	/**
	 * Return a product category by id.
	 * 
	 * @param id - the id of the product category that is retrieved.
	 * @return The DTO of the retrieved product category.
	 */
	ApplicationProductCategoryDTO getProductCategoryById(Long id);

	/**
	 * Updates a product category.
	 * 
	 * @param id - the id of the category being updated.
	 * @param productCategoryDTO - product category DTO object.
	 * @return The DTO of the updated category.
	 */
	ApplicationProductCategoryDTO updateProductCategory(Long id, ApplicationProductCategoryDTO productCategoryDTO);
}