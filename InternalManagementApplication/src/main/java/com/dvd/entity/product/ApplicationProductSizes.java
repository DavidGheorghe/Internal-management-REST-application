package com.dvd.entity.product;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Defines the product's sizes. This is an embedded object in Product entity.
*
* @author David Gheorghe
*/
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationProductSizes {
	private Double height;
	private Double width;
	private Double diameter;
	private Double weight;
}