package com.dvd.utils;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dvd.exception.ResourceNotFoundException;

/**
* Common methods that are used within the application.
*
* @author David Gheorghe
*/
public class UtilsMethods {
	
	public static boolean isStringFieldValidForUpdate(String newValue, String oldValue) {
		boolean isValid = false;
		if (newValue != null && !newValue.isBlank() && !newValue.equals(oldValue)) {
			isValid = true;
		}
		return isValid;
	}

	public static <T> boolean isFieldValidForUpdate(T newValue, T oldValue) {
		boolean isValid = false;
		if (newValue != null && !newValue.equals(oldValue)) {
			isValid = true;
		}
		return isValid;
	}

	public static <T> T getResourceByIdOrElseThrow(JpaRepository<T, Long> repository, Long id, String resourceName) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(resourceName, "id", String.valueOf(id)));
	}
}