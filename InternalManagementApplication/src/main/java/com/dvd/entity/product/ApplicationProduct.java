package com.dvd.entity.product;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.dvd.entity.ApplicationProductCategory;

import lombok.Data;

/**
* Defines the Product resource.
*
* @author David Gheorghe
*/
@Data
@Entity
@Table(name = "Product")
public class ApplicationProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@OneToOne
	@JoinColumn(name = "product_category_id", referencedColumnName = "id")
	private ApplicationProductCategory category;
	
	@Embedded
	private ApplicationProductSizes productSizes;
	
	@Embedded
	private ApplicationProductPrices productPrices;
}