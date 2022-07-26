package com.dvd.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dvd.DTO.ApplicationTodoDTO;
import com.dvd.service.ApplicationTodoService;
import com.dvd.utils.ApplicationConstants;

import lombok.RequiredArgsConstructor;

/**
* Defines the controller layer for the Todo resource.
*
* @author David Gheorghe
*/
@RequiredArgsConstructor
@RestController
@RequestMapping(ApplicationConstants.API_TODO_ROOT)
public class ApplicationTodoController {
	private final ApplicationTodoService todoService;
	
	@PostMapping
	public ResponseEntity<ApplicationTodoDTO> createTodo(@RequestBody ApplicationTodoDTO todoDTO) {
		ApplicationTodoDTO newTodo = todoService.createTodo(todoDTO);
		return new ResponseEntity<ApplicationTodoDTO>(newTodo, HttpStatus.CREATED);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<List<ApplicationTodoDTO>> getAllTodosByUserId(@PathVariable Long userId) {
		List<ApplicationTodoDTO> todos = todoService.getAllTodosByUserId(userId);
		return new ResponseEntity<List<ApplicationTodoDTO>>(todos, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApplicationTodoDTO> deleteById(@PathVariable Long id) {
		ApplicationTodoDTO deletedTodoDTO = todoService.deleteById(id);
		return new ResponseEntity<ApplicationTodoDTO>(deletedTodoDTO, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApplicationTodoDTO> updateTodo(@PathVariable Long id, @RequestBody ApplicationTodoDTO todoDTO) {
		ApplicationTodoDTO updatedTodo = todoService.updateTodo(id, todoDTO);
		return new ResponseEntity<ApplicationTodoDTO>(updatedTodo, HttpStatus.OK);
	}
}
