package com.dvd.DTO;

import lombok.Data;

/**
* Defines the DTO representation of Color resource.
*
* @author David Gheorghe
*/
@Data
public class ApplicationColorDTO {
	private Long id;
	private String name;
	private String firstPigment;
	private Double firstPigmentPercentage;
	private String secondPigment;
	private Double secondPigmentPercentage;
	private String thirdPigment;
	private Double thirdPigmentPercentage;
}