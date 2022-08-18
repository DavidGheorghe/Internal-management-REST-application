package com.dvd.DTO.reports;

import lombok.Data;

/**
* Defines the DTO to store the current number of active orders, and the number of order due in one week.
*
* @author David Gheorghe
*/
@Data
public class ActiveAndDueOrdersReportsDTO {
	private int numberOfActiveOrders;
	private int numberOfDueOrders;
}
