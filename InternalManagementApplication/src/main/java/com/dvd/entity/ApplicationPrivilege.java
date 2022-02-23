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
	EMPLOYEE_READ("employee_read"),
	EMPLOYEE_READ_OWN("employee_read_own"),
	EMPLOYEE_WRITE("employee_write"),
	PRODUCT_READ("product_read"),
	PRODUCT_WRITE("product_write"),
	ORDER_READ("order_read"),
	ORDER_WRITE("order_write"),
	CUSTOMER_READ("customer_read"),
	CUSTOMER_WRITE("customer_write"),
	USER_READ("user_read"),
	USER_READ_OWN("user_read_own"),
	USER_WRITE("user_write");
	
	private final String privilege;
	
	ApplicationPrivilege(String privilege) {
		this.privilege = privilege;
	}
	
	@JsonCreator
	public static ApplicationPrivilege decode(final String privilege) {
		return Stream.of(ApplicationPrivilege.values())
				.filter(privilegeTarget -> privilegeTarget.getPrivilege().equals(privilege.toLowerCase()))
				.findFirst()
				.orElseThrow(() -> new ResourceNotFoundException("Privilege", "name", privilege));//.orElseThrow()
	}
	
	@JsonValue
	public String getPrivilege() {
		return privilege;
	}
}
