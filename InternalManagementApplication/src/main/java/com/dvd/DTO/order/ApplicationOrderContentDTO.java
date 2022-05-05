package com.dvd.DTO.order;

import lombok.Data;

/**
* Defines the DTO of an order content.
*
* @author David Gheorghe
*/
@Data
public class ApplicationOrderContentDTO {
	private Long productId;
	private Long colorId;
	private Integer quantity;
}
