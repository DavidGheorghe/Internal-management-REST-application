package com.dvd.DTO.order;

import com.dvd.DTO.ApplicationColorDTO;
import com.dvd.DTO.ApplicationProductDTO;

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
	private ApplicationProductDTO product;
	private Integer quantity;
}
