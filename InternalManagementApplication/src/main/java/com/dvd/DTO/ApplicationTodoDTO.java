package com.dvd.DTO;

import lombok.Data;

/**
* Defines the DTO for Todo resource.
*
* @author David Gheorghe
*/
@Data
public class ApplicationTodoDTO {
	Long id;
	Long userId;
	String text;
	boolean isCompleted;
}
