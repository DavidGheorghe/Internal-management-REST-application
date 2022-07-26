package com.dvd.service;

import java.util.List;

import com.dvd.DTO.ApplicationTodoDTO;

/**
* Defines the service layer for the Todo resource.
*
* @author David Gheorghe
*/
public interface ApplicationTodoService {
	ApplicationTodoDTO createTodo(ApplicationTodoDTO todoDTO);
	List<ApplicationTodoDTO> getAllTodosByUserId(Long userId);
	ApplicationTodoDTO updateTodo(Long id, ApplicationTodoDTO todoDTO);
	ApplicationTodoDTO deleteById(Long id);
	ApplicationTodoDTO completeTodo(Long id);
}
