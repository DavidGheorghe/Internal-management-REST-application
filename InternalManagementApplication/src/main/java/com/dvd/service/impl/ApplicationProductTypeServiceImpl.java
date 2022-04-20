package com.dvd.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dvd.DTO.ApplicationProductTypeDTO;
import com.dvd.DTO.GetResourcesResponse;
import com.dvd.entity.ApplicationProductType;
import com.dvd.exception.UniqueEntryException;
import com.dvd.repository.ApplicationProductTypeRepository;
import com.dvd.service.ApplicationProductTypeService;
import com.dvd.utils.UtilsMethods;

import lombok.RequiredArgsConstructor;

/**
* Defines the implementation of the service layer for ProductType resource.
*
* @author David Gheorghe
*/
@RequiredArgsConstructor
@Service
public class ApplicationProductTypeServiceImpl implements ApplicationProductTypeService {
	private final ApplicationProductTypeRepository productTypeRepository;
	private final ModelMapper mapper;
	
	@Override
	public ApplicationProductTypeDTO createProductType(ApplicationProductTypeDTO productTypeDTO) {
		ApplicationProductType newType = mapper.map(productTypeDTO, ApplicationProductType.class);
		if (productTypeRepository.existsByTypeName(productTypeDTO.getName())) {
			throw new UniqueEntryException(productTypeDTO.getName());
		}
		productTypeRepository.save(newType);
		return mapper.map(newType, ApplicationProductTypeDTO.class);
	}

	@Override
	public ApplicationProductTypeDTO deleteById(Long id) {
		ApplicationProductType deletedType = UtilsMethods.getResourceByIdOrElseThrow(productTypeRepository, id, "ProductType");
		ApplicationProductTypeDTO deletedTypeDTO = mapper.map(deletedType, ApplicationProductTypeDTO.class);
		productTypeRepository.deleteById(id);
		return deletedTypeDTO;
	}

	@Override
	public GetResourcesResponse<ApplicationProductTypeDTO> getAllProductTypes(int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<ApplicationProductType> types = productTypeRepository.findAll(pageable);
		List<ApplicationProductType> listOfTypes = types.getContent();
		List<ApplicationProductTypeDTO> content = listOfTypes
													.stream()
													.map(productType -> mapper.map(productType, ApplicationProductTypeDTO.class))
													.collect(Collectors.toList());
		GetResourcesResponse<ApplicationProductTypeDTO> response = new GetResourcesResponse<>();
		response.setGetResourcesResponseFields(content, types);
		return response;
	}

	@Override
	public ApplicationProductTypeDTO getProductTypeById(Long id) {
		ApplicationProductType retrievedType = UtilsMethods.getResourceByIdOrElseThrow(productTypeRepository, id, "ProductType");
		return mapper.map(retrievedType, ApplicationProductTypeDTO.class);
	}

	@Override
	public ApplicationProductTypeDTO updateProductType(Long id, ApplicationProductTypeDTO productTypeDTO) {
		ApplicationProductType updatedType = UtilsMethods.getResourceByIdOrElseThrow(productTypeRepository, id, "ProductType");
		if (UtilsMethods.isStringFieldValidForUpdate(updatedType.getTypeName(), productTypeDTO.getName())) {
			updatedType.setTypeName(productTypeDTO.getName());
			productTypeRepository.save(updatedType);
		}
		return mapper.map(updatedType, ApplicationProductTypeDTO.class);
	}
}