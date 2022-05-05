package com.dvd.DTO;

import com.dvd.entity.ApplicationProductCategory;
import com.dvd.entity.product.ApplicationProductPrices;
import com.dvd.entity.product.ApplicationProductSizes;

import lombok.Data;

/**
* Defines the DTO for a retrieved product.
*
* @author David Gheorghe
*/
@Data
public class RetrievedApplicationProductDTO {
	private Long id;
	private String name;
	private ApplicationProductCategory productCategory;
	private ApplicationProductSizes productSizes;
	private ApplicationProductPrices productPrices;
}
