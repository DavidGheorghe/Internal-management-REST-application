package com.dvd.service;

import java.util.List;

import com.dvd.DTO.ApplicationProductDTO;
import com.dvd.DTO.GetResourcesResponse;
import com.dvd.DTO.RetrievedApplicationProductDTO;
import com.dvd.DTO.CategoriesIdsDTO;

/**
* Defines the service layer for the Product resource.
*
* @author David Gheorghe
*/
public interface ApplicationProductService {

	/**
	 * Creates a new product.
	 * 
	 * @param productDTO - product DTO object.
	 * @return The new product as DTO.
	 */
	RetrievedApplicationProductDTO createProduct(ApplicationProductDTO productDTO);

	/**
	 * Deletes a product by id.
	 * 
	 * @param id - the id of the product that must be deleted.
	 * @return The deleted product as DTO.
	 */
	RetrievedApplicationProductDTO deleteById(Long id);

	/**
	 * Returns the products. The response is based on pagination and sorting.
	 * Go to {@link com.dvd.utils.ApplicationConstants} class to see the parameters default values.
	 * 
	 * @param pageNo	- page number.
	 * @param pageSize 	- page size.
	 * @param sortBy 	- criteria for sorting.
	 * @param sortDir 	- sorting direction  (ASC or DESC).
	 * @return A custom response with products.
	 */
	GetResourcesResponse<RetrievedApplicationProductDTO> getProducts(int pageNo, int pageSize, String sortBy, String sortDir);
	
	List<RetrievedApplicationProductDTO> getAllProducts();

	/**
	 * Returns a product by id.
	 * 
	 * @param id - the id of the retrieved product.
	 * @return The DTO of the retrieved product.
	 */
	RetrievedApplicationProductDTO getProductById(Long id);

	/**
	 * Updates a product.
	 * 
	 * @param id 			- the id of the product being updated.
	 * @param productDTO 	- product DTO object.
	 * @return The updated product as DTO.
	 */
	RetrievedApplicationProductDTO updateProduct(Long id, ApplicationProductDTO productDTO);
	
	
	/**
	 * Return the products based on its category id. The response is based on pagination and sorting.
	 * Go to {@link com.dvd.utils.ApplicationConstants} class to see the parameters default values.
	 * 
	 * @param productCategoryId	- the category id.
	 * @param pageNo			- page number.
	 * @param pageSize 			- page size.
	 * @param sortBy 			- criteria for sorting.
	 * @param sortDir 			- sorting direction  (ASC or DESC).
	 * @return A custom response with the products.
	 */
	GetResourcesResponse<RetrievedApplicationProductDTO> getAllProductsByCategoryIdAndFilteredBy(Long productCategoryId, String keyword, int pageNo, int pageSize, String sortBy, String sortDir);
	
	GetResourcesResponse<RetrievedApplicationProductDTO> getProductsByCategories(CategoriesIdsDTO productDTO, int pageNo, int pageSize, String sortBy, String sortDir);
	
	/**
	 * Return the products which name contains the specified keyword. The response is based on pagination and sorting.
	 * Go to {@link com.dvd.utils.ApplicationConstants} class to see the parameters default values.
	 * 
	 * @param keyword	- the keyword based on which the products are filtered.
	 * @param pageNo	- page number.
	 * @param pageSize 	- page size.
	 * @param sortBy 	- criteria for sorting.
	 * @param sortDir 	- sorting direction  (ASC or DESC).
	 * @return
	 */
	GetResourcesResponse<RetrievedApplicationProductDTO> getProductsSearchedBy(String keyword, int pageNo, int pageSize, String sortBy, String sortDir);
}
