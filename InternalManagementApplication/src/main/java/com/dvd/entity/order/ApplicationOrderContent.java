package com.dvd.entity.order;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.dvd.entity.ApplicationColor;
import com.dvd.entity.product.ApplicationProduct;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
* Defines the content of an order.
*
* @author David Gheorghe
*/
@Data
@Entity
@Table(name = "Orders_contents")
public class ApplicationOrderContent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
	private ApplicationProduct product;
	
	@OneToOne
	@JoinColumn(name = "color_id", referencedColumnName = "id", nullable = false)
	private ApplicationColor color;
	
	private Integer quantity;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
	private ApplicationOrder order;
}
