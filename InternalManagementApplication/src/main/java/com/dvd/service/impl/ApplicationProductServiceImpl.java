package com.dvd.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dvd.DTO.ApplicationProductDTO;
import com.dvd.DTO.GetResourcesResponse;
import com.dvd.DTO.RetrievedApplicationProductDTO;
import com.dvd.entity.ApplicationProductCategory;
import com.dvd.entity.product.ApplicationProduct;
import com.dvd.entity.product.ApplicationProductPrices;
import com.dvd.entity.product.ApplicationProductSizes;
import com.dvd.repository.ApplicationProductRepository;
import com.dvd.repository.ApplicationProductCategoryRepository;
import com.dvd.service.ApplicationProductService;
import com.dvd.utils.UtilsMethods;

import lombok.RequiredArgsConstructor;

/**
* Defines the implementation of the service layer for the Product resource.
*
* @author David Gheorghe
*/
@RequiredArgsConstructor
@Service
public class ApplicationProductServiceImpl implements ApplicationProductService {
	private final ApplicationProductRepository productRepository;
	private final ApplicationProductCategoryRepository productTypeRepository;
	private final ModelMapper mapper;
	
	@Override
	public RetrievedApplicationProductDTO createProduct(ApplicationProductDTO productDTO) {
		ApplicationProduct newProduct = new ApplicationProduct();
		newProduct.setName(productDTO.getName());
		newProduct.setProductPrices(createProductPricesFromDTO(productDTO));
		newProduct.setProductSizes(createProductSizesFromDTO(productDTO));
		ApplicationProductCategory productType = UtilsMethods.getResourceByIdOrElseThrow(productTypeRepository, productDTO.getProductCategoryId(), "Product Type");
		newProduct.setCategory(productType);
		ApplicationProduct createdProduct = productRepository.save(newProduct);
		return mapper.map(createdProduct, RetrievedApplicationProductDTO.class);
	}

	@Override
	public RetrievedApplicationProductDTO deleteById(Long id) {
		ApplicationProduct deletedProduct = UtilsMethods.getResourceByIdOrElseThrow(productRepository, id, "Product");
		RetrievedApplicationProductDTO deletedProductDTO = mapper.map(deletedProduct, RetrievedApplicationProductDTO.class);
		productRepository.deleteById(id);
		return deletedProductDTO;
	}

	@Override
	public GetResourcesResponse<RetrievedApplicationProductDTO> getAllProducts(int pageNo, int pageSize, String sortBy,
			String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<ApplicationProduct> products = productRepository.findAll(pageable);
		List<ApplicationProduct> listOfProducts = products.getContent();
		List<RetrievedApplicationProductDTO> content = listOfProducts
													.stream()
													.map(product -> mapper.map(product, RetrievedApplicationProductDTO.class))
													.collect(Collectors.toList());
		GetResourcesResponse<RetrievedApplicationProductDTO> response = new GetResourcesResponse<>();
		response.setGetResourcesResponseFields(content, products);
		return response;
	}

	@Override
	public RetrievedApplicationProductDTO getProductById(Long id) {
		ApplicationProduct retrievedProduct = UtilsMethods.getResourceByIdOrElseThrow(productRepository, id, "Product");
		return mapper.map(retrievedProduct, RetrievedApplicationProductDTO.class);
	}

	@Override
	public RetrievedApplicationProductDTO updateProduct(Long id, ApplicationProductDTO productDTO) {
		ApplicationProduct updatedProduct = UtilsMethods.getResourceByIdOrElseThrow(productRepository, id, "Product");
		if (UtilsMethods.isStringFieldValidForUpdate(productDTO.getName(), updatedProduct.getName())) {
			updatedProduct.setName(productDTO.getName());
		}
		if (productDTO.getProductCategoryId() != null) {
			updateProductCategory(updatedProduct, productDTO.getProductCategoryId());
		}
		if (areProductPricesUpdated(productDTO)) {
			updateProductPrices(updatedProduct, productDTO);
		}
		if (areProductSizesUpdated(productDTO)) {
			updateProductSizes(updatedProduct, productDTO);
		}
		productRepository.save(updatedProduct);
		return mapper.map(updatedProduct, RetrievedApplicationProductDTO.class);
	}
	
	/**
	 * Creates a {@link com.dvd.entity.product.ProductSizes} object from DTO.
	 * 
	 * @param productDTO - the DTO.
	 * @return The {@link com.dvd.entity.product.ProductSizes} object.
	 */
	private ApplicationProductSizes createProductSizesFromDTO(ApplicationProductDTO productDTO) {
		Double height = productDTO.getHeight();
		Double width = productDTO.getWidth();
		Double diameter = productDTO.getDiameter();
		Double weight = productDTO.getWeight();		
		return new ApplicationProductSizes(height, width, diameter, weight);
	}
	
	/**
	 * Creates a {@link com.dvd.entity.product.ProductPrices} object from DTO.
	 * 
	 * @param productDTO - the DTO.
	 * @return The {@link com.dvd.entity.product.ProductPrices} object.
	 */
	private ApplicationProductPrices createProductPricesFromDTO(ApplicationProductDTO productDTO) {
		Double priceWithoutVAT = productDTO.getPriceWithoutVAT();
		Double productionCost = productDTO.getProductionCost();
		return new ApplicationProductPrices(productionCost, priceWithoutVAT);
	}
	
	/**
	 * Updates the category of the product.
	 * 
	 * @param updatedProduct - the product being updated.
	 * @param productCategoryId - the id of the product category.
	 */
	private void updateProductCategory(ApplicationProduct updatedProduct, Long productCategoryId) {
		ApplicationProductCategory productCategory = UtilsMethods.getResourceByIdOrElseThrow(productTypeRepository, productCategoryId, "Product Category");
		if (!productCategory.equals(updatedProduct.getCategory())) {
			updatedProduct.setCategory(productCategory);
		}
	}
	
	/**
	 * Updates the sizes of a product.
	 * 
	 * @param updatedProduct - the product being updated.
	 * @param productDTO - the DTO with new sizes.
	 */
	private void updateProductSizes(ApplicationProduct updatedProduct, ApplicationProductDTO productDTO) {
		if (UtilsMethods.isFieldValidForUpdate(productDTO.getDiameter(), updatedProduct.getProductSizes().getDiameter())) {
			updatedProduct.getProductSizes().setDiameter(productDTO.getDiameter());
		}
		if (UtilsMethods.isFieldValidForUpdate(productDTO.getHeight(), updatedProduct.getProductSizes().getHeight())) {
			updatedProduct.getProductSizes().setHeight(productDTO.getHeight());
		}
		if (UtilsMethods.isFieldValidForUpdate(productDTO.getWeight(), updatedProduct.getProductSizes().getWeight())) {
			updatedProduct.getProductSizes().setWeight(productDTO.getWeight());
		}
		if (UtilsMethods.isFieldValidForUpdate(productDTO.getWidth(), updatedProduct.getProductSizes().getWidth())) {
			updatedProduct.getProductSizes().setWidth(productDTO.getWidth());
		}
	}
	
	/**
	 * Updates the prices of a product.
	 * 
	 * @param updatedProduct - the product being updated.
	 * @param productDTO - the DTO with new prices.
	 */
	private void updateProductPrices(ApplicationProduct updatedProduct, ApplicationProductDTO productDTO) {
		if (UtilsMethods.isFieldValidForUpdate(productDTO.getProductionCost(), updatedProduct.getProductPrices().getProductionCost())) {
			updatedProduct.getProductPrices().setProductionCost(productDTO.getProductionCost());
		}
		if (UtilsMethods.isFieldValidForUpdate(productDTO.getPriceWithoutVAT(), updatedProduct.getProductPrices().getPriceWithoutVAT())) {
			updatedProduct.getProductPrices().setPriceWithoutVAT(productDTO.getPriceWithoutVAT());
		}
	}
	
	/**
	 * Checks if the sizes are updated.
	 * 
	 * @param productDTO - the DTO.
	 * @return true if the sizes are updated, false otherwise.
	 */
	private boolean areProductSizesUpdated(ApplicationProductDTO productDTO) {
		boolean areSizesUpdated = false;
		if (productDTO.getDiameter() != null || productDTO.getHeight() != null || productDTO.getWeight() != null || productDTO.getWidth() != null) {
			areSizesUpdated = true;
		}
		return areSizesUpdated;
	}
	
	/**
	 * Checks if the prices are updated.
	 * 
	 * @param productDTO - the DTO.
	 * @return true if the prices are updated, false otherwise.
	 */
	private boolean areProductPricesUpdated(ApplicationProductDTO productDTO) {
		boolean arePricesUpdated = false;
		if (productDTO.getProductionCost() != null || productDTO.getPriceWithoutVAT() != null) {
			arePricesUpdated = true;
		}
		return arePricesUpdated;
	}
}
