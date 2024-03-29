package com.dvd.DTO.order;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.dvd.DTO.ApplicationCustomerDTO;
import com.dvd.DTO.ApplicationUserDTO;
import com.dvd.entity.order.ApplicationOrderStatus;

import lombok.Data;

/**
* Defines the DTO of an retrieved order.
*
* @author David Gheorghe
*/
@Data
public class RetrievedOrderDTO {
	private Long id;
	private ApplicationOrderStatus status;
	private LocalDate dueDate;
	private LocalDateTime entryDate;
	private String details;
	private ApplicationCustomerDTO customer;
	private ApplicationUserDTO assignee;
	private boolean isPinned;
}
