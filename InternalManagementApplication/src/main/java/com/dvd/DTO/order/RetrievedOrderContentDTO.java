package com.dvd.DTO.order;

import com.dvd.DTO.ApplicationColorDTO;
import com.dvd.DTO.RetrievedApplicationProductDTO;

import lombok.Data;

/**
* Defines the DTO for an retrieved order content.
*
* @author David Gheorghe
*/
@Data
public class RetrievedOrderContentDTO {
	private Long id;
	private ApplicationColorDTO color;
	private RetrievedApplicationProductDTO product;
	private Integer quantity;
	private Double contentPrice;
}
