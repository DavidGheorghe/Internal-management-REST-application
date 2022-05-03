package com.dvd.entity;

import java.util.stream.Stream;

import com.dvd.exception.ResourceNotFoundException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
* Defines the privileges available in the application.
*
* @author David Gheorghe
*/
public enum ApplicationPrivilege {
	PRODUCT_READ(1, "product_read"),
	PRODUCT_WRITE(2, "product_write"),
	ORDER_READ(3, "order_read"),
	ORDER_WRITE(4, "order_write"),
	CUSTOMER_READ(5, "customer_read"),
	CUSTOMER_WRITE(6, "customer_write"),
	USER_READ(7, "user_read"),
	USER_READ_OWN(8, "user_read_own"),
	USER_WRITE(9, "user_write");
	
	private final Integer id;
	private final String privilege;
	
	ApplicationPrivilege(Integer id, String privilege) {
		this.id = id;
		this.privilege = privilege;
	}
	
	@JsonCreator
	public static ApplicationPrivilege decode(final String privilege) {
		return Stream.of(ApplicationPrivilege.values())
				.filter(privilegeTarget -> privilegeTarget.getPrivilege().equals(privilege.toLowerCase()))
				.findFirst()
				.orElseThrow(() -> new ResourceNotFoundException("Privilege", "name", privilege));
	}
	
	@JsonValue
	public String getPrivilege() {
		return privilege;
	}
	
	public Integer getId() {
		return id;
	}
}
