package com.dvd.DTO.order;

import java.util.Set;

import lombok.Data;

/**
* Defines the DTO for creating an order.
*
* @author David Gheorghe
*/
@Data
public class ApplicationOrderDTO {
	private Integer statusId;
	private String dueDate;
	private String details;
	private Long customerId;
	private Set<ApplicationOrderContentDTO> content;
}
