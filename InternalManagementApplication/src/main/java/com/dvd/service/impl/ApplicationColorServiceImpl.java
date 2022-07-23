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

import com.dvd.DTO.ApplicationColorDTO;
import com.dvd.DTO.GetResourcesResponse;
import com.dvd.entity.ApplicationColor;
import com.dvd.repository.ApplicationColorRepository;
import com.dvd.service.ApplicationColorService;
import com.dvd.utils.UtilsMethods;

import lombok.RequiredArgsConstructor;

/**
* Defines the implementation of the service layer for Color resource.
*
* @author David Gheorghe
*/
@RequiredArgsConstructor
@Service
public class ApplicationColorServiceImpl implements ApplicationColorService {
	private final ApplicationColorRepository colorRepository;
	private final ModelMapper mapper;

	@Override
	public ApplicationColorDTO createColor(ApplicationColorDTO colorDTO) {
		ApplicationColor createdColor = createColorFromDTO(colorDTO);
		colorRepository.save(createdColor);
		return mapper.map(createdColor, ApplicationColorDTO.class);
	}

	@Override
	public ApplicationColorDTO deleteById(Long id) {
		ApplicationColor deletedColor = UtilsMethods.getResourceByIdOrElseThrow(colorRepository, id, "Color");
		ApplicationColorDTO deletedColorDTO = mapper.map(deletedColor, ApplicationColorDTO.class);
		colorRepository.deleteById(id);
		return deletedColorDTO;
	}

//	@Override
//	public GetResourcesResponse<ApplicationColorDTO> getColors(int numberOfPigments, int pageNo, int pageSize, String sortBy, String sortDir) {
//		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
//		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
//		Page<ApplicationColor> colors = numberOfPigments == 3 ? colorRepository.findAll(pageable) : 
//																(numberOfPigments == 2 ? 
//																		colorRepository.findAllByFirstAndSecondPigment(pageable) : colorRepository.findAllByFirstPigment(pageable));
//		List<ApplicationColor> listOfColors = colors.getContent();
//		List<ApplicationColorDTO> content = listOfColors
//													.stream()
//													.map(color -> mapper.map(color, ApplicationColorDTO.class))
//													.collect(Collectors.toList());
//		GetResourcesResponse<ApplicationColorDTO> response = new GetResourcesResponse<>();
//		response.setGetResourcesResponseFields(content, colors);
//		return response;
//	}

	@Override
	public List<ApplicationColorDTO> getAllColors() {
		List<ApplicationColorDTO> colorsDTOs = new ArrayList<>();
		List<ApplicationColor> colors = colorRepository.findAll();
		colors.stream().forEach(color -> colorsDTOs.add(mapper.map(color, ApplicationColorDTO.class)));
		return colorsDTOs;
	}

	@Override
	public ApplicationColorDTO getColorById(Long id) {
		ApplicationColor color = UtilsMethods.getResourceByIdOrElseThrow(colorRepository, id, "Color");
		return mapper.map(color, ApplicationColorDTO.class);
	}

	@Override
	public GetResourcesResponse<ApplicationColorDTO> getColorsFilteredBy(String keyword, int pageNo, int pageSize,
			String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<ApplicationColor> colorsPage = keyword == null ? colorRepository.findAll(pageable) : colorRepository.findAllByNameContains(keyword, pageable);
		List<ApplicationColor> listOfColors = colorsPage.getContent();
		List<ApplicationColorDTO> content = listOfColors
													.stream()
													.map(color -> mapper.map(color, ApplicationColorDTO.class))
													.collect(Collectors.toList());
		GetResourcesResponse<ApplicationColorDTO> response = new GetResourcesResponse<>();
		response.setGetResourcesResponseFields(content, colorsPage);
		return response;
	}
	
	@Override
	public GetResourcesResponse<ApplicationColorDTO> getColorsWithOnePigmentFilteredBy(String keyword, int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<ApplicationColor> colorsPage = keyword == null ? colorRepository.findAllByFirstPigment(pageable) : colorRepository.findAllByFirstPigmentFilteredBy(keyword, pageable);
		List<ApplicationColor> listOfColors = colorsPage.getContent();
		List<ApplicationColorDTO> content = listOfColors
													.stream()
													.map(colors -> mapper.map(colors, ApplicationColorDTO.class))
													.collect(Collectors.toList());
		GetResourcesResponse<ApplicationColorDTO> response = new GetResourcesResponse<>();
		response.setGetResourcesResponseFields(content, colorsPage);
		return response;
	}
	
	@Override
	public GetResourcesResponse<ApplicationColorDTO> getColorsWithTwoPigmentsFilteredBy(String keyword, int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<ApplicationColor> colorsPage = keyword == null ? colorRepository.findAllByFirstAndSecondPigment(pageable) : colorRepository.findAllByFirstAndSecondPigmentFilteredBy(keyword, pageable);
		List<ApplicationColor> listOfColors = colorsPage.getContent();
		List<ApplicationColorDTO> content = listOfColors
													.stream()
													.map(colors -> mapper.map(colors, ApplicationColorDTO.class))
													.collect(Collectors.toList());
		GetResourcesResponse<ApplicationColorDTO> response = new GetResourcesResponse<>();
		response.setGetResourcesResponseFields(content, colorsPage);
		return response;
	}

	@Override
	public GetResourcesResponse<ApplicationColorDTO> getColorsWithThreePigmentsFilteredBy(String keyword, int pageNo,
			int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<ApplicationColor> colorsPage = keyword == null ? colorRepository.findAllByAllPigments(pageable) : colorRepository.findAllByAllPigmentsFilteredBy(keyword, pageable);
		List<ApplicationColor> listOfColors = colorsPage.getContent();
		List<ApplicationColorDTO> content = listOfColors
													.stream()
													.map(colors -> mapper.map(colors, ApplicationColorDTO.class))
													.collect(Collectors.toList());
		GetResourcesResponse<ApplicationColorDTO> response = new GetResourcesResponse<>();
		response.setGetResourcesResponseFields(content, colorsPage);
		return response;
	}

	@Override
	public ApplicationColorDTO updateColor(Long id, ApplicationColorDTO colorDTO) {
		ApplicationColor updatedColor = UtilsMethods.getResourceByIdOrElseThrow(colorRepository, id, "Color"); 
		String newColor = colorDTO.getName();
		String oldColor = updatedColor.getName();
		/** Update the name if necessary. */
		if (UtilsMethods.isStringFieldValidForUpdate(newColor, oldColor)) {
			updatedColor.setName(newColor);
		}
		/** Update the pigments. */
		updatePigment(updatedColor, 1, colorDTO.getFirstPigment(), updatedColor.getFirstPigment());
		updatePigment(updatedColor, 2, colorDTO.getSecondPigment(), updatedColor.getSecondPigment());
		updatePigment(updatedColor, 3, colorDTO.getThirdPigment(), updatedColor.getThirdPigment());
		/** Update the pigments percentages. */
		updatePigmentPercentage(updatedColor, 1, colorDTO.getFirstPigmentPercentage(), updatedColor.getFirstPigmentPercentage());
		updatePigmentPercentage(updatedColor, 2, colorDTO.getSecondPigmentPercentage(), updatedColor.getSecondPigmentPercentage());
		updatePigmentPercentage(updatedColor, 3, colorDTO.getThirdPigmentPercentage(), updatedColor.getThirdPigmentPercentage());
		colorRepository.save(updatedColor);
		return mapper.map(updatedColor, ApplicationColorDTO.class);
	}

	/**
	 * Creates a color based on DTO object. It can be with one, two or three pigments.
	 * 
	 * @param colorDTO - the DTO.
	 * @return the new color.
	 */
	private ApplicationColor createColorFromDTO(ApplicationColorDTO colorDTO) {
		String colorName = colorDTO.getName();
		ApplicationColor newColor = new ApplicationColor(colorName, colorDTO.getFirstPigment(), colorDTO.getFirstPigmentPercentage());
		if (colorDTO.getSecondPigment() != null) {
			newColor.setSecondPigment(colorDTO.getSecondPigment());
			newColor.setSecondPigmentPercentage(colorDTO.getSecondPigmentPercentage());
		}
		if (colorDTO.getThirdPigment() != null) {
			newColor.setThirdPigment(colorDTO.getThirdPigment());
			newColor.setThirdPigmentPercentage(colorDTO.getThirdPigmentPercentage());
		}
		return newColor;
	}

	/**
	 * Updates the pigment.
	 * 
	 * @param updatedColor - the color being updated.
	 * @param pigmentNo - pigment number (1, 2 or 3).
	 * @param newPigment - the new pigment value.
	 * @param oldPigment - the old pigment value.
	 */
	private void updatePigment(ApplicationColor updatedColor, int pigmentNo, String newPigment, String oldPigment) {
		switch (pigmentNo) {
			case 1:
				if (UtilsMethods.isStringFieldValidForUpdate(newPigment, oldPigment)) {
					updatedColor.setFirstPigment(newPigment);
				}
				break;
			case 2:
				if (UtilsMethods.isStringFieldValidForUpdate(newPigment, oldPigment)) {
					updatedColor.setSecondPigment(newPigment);
				}
				break;
			case 3:
				if (UtilsMethods.isStringFieldValidForUpdate(newPigment, oldPigment)) {
					updatedColor.setThirdPigment(newPigment);
				}
				break;
		}
	}

	/**
	 * Updates the pigment percentage.
	 * 
	 * @param updatedColor - the color being updated.
	 * @param pigmentNo - pigment number (1, 2, or 3).
	 * @param newPigmentPercentage - the new pigment percentage value.
	 * @param oldPigmentPercentage - the old pigment percentage value.
	 */
	private void updatePigmentPercentage(ApplicationColor updatedColor, int pigmentNo, Double newPigmentPercentage, Double oldPigmentPercentage) {
		switch (pigmentNo) {
			case 1:
				if (UtilsMethods.isFieldValidForUpdate(newPigmentPercentage, oldPigmentPercentage)) {
					updatedColor.setFirstPigmentPercentage(newPigmentPercentage);
				}
				break;
			case 2:
				if (UtilsMethods.isFieldValidForUpdate(newPigmentPercentage, oldPigmentPercentage)) {
					updatedColor.setSecondPigmentPercentage(newPigmentPercentage);
				}
				break;
			case 3:
				if (UtilsMethods.isFieldValidForUpdate(newPigmentPercentage, oldPigmentPercentage)) {
					updatedColor.setThirdPigmentPercentage(newPigmentPercentage);
				}
				break;
		}
	}


}