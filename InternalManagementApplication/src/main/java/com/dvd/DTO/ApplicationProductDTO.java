package com.dvd.DTO;

import lombok.Data;

/**
* Defines the DTO representation of Product resource.
*
* @author David Gheorghe
*/
@Data
public class ApplicationProductDTO {
	private String name;
	private Long productCategoryId;
	private Double height;
	private Double weight;
	private Double diameter;
	private Double priceWithoutVAT;
	private Double productionCost;
}