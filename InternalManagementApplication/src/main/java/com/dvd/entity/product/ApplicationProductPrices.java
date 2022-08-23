package com.dvd.entity.product;

import java.text.DecimalFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.dvd.utils.ApplicationConstants;

import lombok.Data;

/**
* Defines the product's prices. This is an embedded object in Product entity.
*
* @author David Gheorghe
*/
@Data
@Embeddable
public class ApplicationProductPrices {
	private Double productionCost;
	@Column(name = "price_without_VAT")
	private Double priceWithoutVAT;
	private Double finalPrice;
	
	private Double computeFinalPrice() {
		DecimalFormat df = new DecimalFormat("0.00");
		Double finalPrice = priceWithoutVAT + ( ApplicationConstants.VAT / 100.0 ) * priceWithoutVAT;
		String formattedFinalPriceLocale = df.format(finalPrice);
		String formattedFinalPrice = formattedFinalPriceLocale.replace(",", ".");
		return Double.valueOf(formattedFinalPrice);
	}
	
	public ApplicationProductPrices(Double productionCost, Double priceWithoutVAT) {
		this.productionCost = productionCost;
		this.priceWithoutVAT = priceWithoutVAT;
		this.finalPrice = computeFinalPrice();
	}
	
	public ApplicationProductPrices() {
	}
}
