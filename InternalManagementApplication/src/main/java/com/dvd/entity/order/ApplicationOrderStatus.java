package com.dvd.entity.order;

import com.dvd.exception.ResourceNotFoundException;

/**
* Defines the status of an order.
*
* @author David Gheorghe
*/
public enum ApplicationOrderStatus {
	NEW(1),
	POURING(2),
	DRYING(3),
	SANDING(4),
	SEALING(5),
	PACKING(6),
	READY(7),
	COMPLETED(8);
	
	private final int id;
	ApplicationOrderStatus(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public static ApplicationOrderStatus getStatusById(int id) {
		for (ApplicationOrderStatus status: ApplicationOrderStatus.values()) {
			if (status.getId() == id) {
				return status;
			}
		}
		throw new ResourceNotFoundException("Order Status", "id", String.valueOf(id));
	}
}
