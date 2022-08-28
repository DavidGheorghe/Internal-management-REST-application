package com.dvd.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dvd.DTO.ApplicationProductCategoryDTO;
import com.dvd.DTO.GetResourcesResponse;
import com.dvd.entity.ApplicationProductCategory;
import com.dvd.exception.UniqueEntryException;
import com.dvd.repository.ApplicationProductCategoryRepository;
import com.dvd.service.ApplicationProductCategoryService;
import com.dvd.utils.UtilsMethods;

import lombok.RequiredArgsConstructor;

/**
* Defines the implementation of the service layer for ProductCategory resource.
*
* @author David Gheorghe
*/
@RequiredArgsConstructor
@Service
public class ApplicationProductCategoryServiceImpl implements ApplicationProductCategoryService {
	private final ApplicationProductCategoryRepository productCategoryRepository;
	private final ModelMapper mapper;
	
	@Override
	public ApplicationProductCategoryDTO createProductCategory(ApplicationProductCategoryDTO productCategoryDTO) {
		ApplicationProductCategory newCategory = new ApplicationProductCategory();
		newCategory.setCategoryName(productCategoryDTO.getCategoryName());
		if (productCategoryRepository.existsByCategoryName(productCategoryDTO.getCategoryName())) {
			throw new UniqueEntryException(productCategoryDTO.getCategoryName());
		}
		productCategoryRepository.save(newCategory);
		return mapper.map(newCategory, ApplicationProductCategoryDTO.class);
	}

	@Override
	public ApplicationProductCategoryDTO deleteById(Long id) {
		ApplicationProductCategory deletedCategory = UtilsMethods.getResourceByIdOrElseThrow(productCategoryRepository, id, "Product Category");
		ApplicationProductCategoryDTO deletedCategoryDTO = mapper.map(deletedCategory, ApplicationProductCategoryDTO.class);
		productCategoryRepository.deleteById(id);
		return deletedCategoryDTO;
	}

	@Override
	public GetResourcesResponse<ApplicationProductCategoryDTO> getAllProductCategories(int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<ApplicationProductCategory> categories = productCategoryRepository.findAll(pageable);
		List<ApplicationProductCategory> listOfCategories = categories.getContent();
		List<ApplicationProductCategoryDTO> content = listOfCategories
													.stream()
													.map(productCategory -> mapper.map(productCategory, ApplicationProductCategoryDTO.class))
													.collect(Collectors.toList());
		GetResourcesResponse<ApplicationProductCategoryDTO> response = new GetResourcesResponse<>();
		response.setGetResourcesResponseFields(content, categories);
		return response;
	}

	@Override
	public ApplicationProductCategoryDTO getProductCategoryById(Long id) {
		ApplicationProductCategory retrievedCategory = UtilsMethods.getResourceByIdOrElseThrow(productCategoryRepository, id, "Product Category");
		return mapper.map(retrievedCategory, ApplicationProductCategoryDTO.class);
	}

	@Override
	public ApplicationProductCategoryDTO updateProductCategory(Long id, ApplicationProductCategoryDTO productCategoryDTO) {
		ApplicationProductCategory updatedCategory = UtilsMethods.getResourceByIdOrElseThrow(productCategoryRepository, id, "Product Category");
		if (UtilsMethods.isStringFieldValidForUpdate(updatedCategory.getCategoryName(), productCategoryDTO.getCategoryName())) {
			updatedCategory.setCategoryName(productCategoryDTO.getCategoryName());
			productCategoryRepository.save(updatedCategory);
		}
		return mapper.map(updatedCategory, ApplicationProductCategoryDTO.class);
	}

	@Override
	public List<String> getCategoriesNames() {
		List<String> names = new ArrayList<>();
		List<ApplicationProductCategory> categories = productCategoryRepository.findAll();
		categories.stream().forEach(category -> names.add(category.getCategoryName()));
		return names;
	}
	
	@Override
	public List<ApplicationProductCategoryDTO> getAllCategoriesWithoutPagination() {
		List<ApplicationProductCategoryDTO> categoriesDTOs = new ArrayList<>();
		List<ApplicationProductCategory> categories = productCategoryRepository.findAll();
		categories.stream().forEach(category -> categoriesDTOs.add(mapper.map(category, ApplicationProductCategoryDTO.class)));
		return categoriesDTOs;
	}
}