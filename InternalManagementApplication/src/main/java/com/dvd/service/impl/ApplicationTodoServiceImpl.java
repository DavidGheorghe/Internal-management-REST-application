package com.dvd.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.dvd.DTO.ApplicationTodoDTO;
import com.dvd.entity.ApplicationTodo;
import com.dvd.entity.ApplicationUser;
import com.dvd.repository.ApplicationTodoRepository;
import com.dvd.repository.ApplicationUserRepository;
import com.dvd.service.ApplicationTodoService;
import com.dvd.utils.UtilsMethods;

import lombok.RequiredArgsConstructor;

/**
* Defines the implementation of the service layer for Todo resource.
*
* @author David Gheorghe
*/
@Service
@RequiredArgsConstructor
public class ApplicationTodoServiceImpl implements ApplicationTodoService {
	
	private final ApplicationTodoRepository todoRepository;
	private final ApplicationUserRepository userRepository;
	private final ModelMapper mapper;
	
	@Override
	public ApplicationTodoDTO createTodo(ApplicationTodoDTO todoDTO) {
		ApplicationTodo newTodo = new ApplicationTodo();
		newTodo.setText(todoDTO.getText());
		
		String username = todoDTO.getUsername();
		ApplicationUser user = userRepository.findByUsername(username);
		newTodo.setUser(user);
		
		todoRepository.save(newTodo);
		return mapper.map(newTodo, ApplicationTodoDTO.class);
	}

	@Override
	public List<ApplicationTodoDTO> getAllTodosByUser(String username) {
		ApplicationUser user = userRepository.findByUsername(username);
		List<ApplicationTodoDTO> todosDTO = new ArrayList<>();
		List<ApplicationTodo> todos = user.getTodos();
		todos.stream().forEach(todo -> todosDTO.add(mapper.map(todo, ApplicationTodoDTO.class)));
		return todosDTO;
	}

	@Override
	public ApplicationTodoDTO updateTodo(Long id, ApplicationTodoDTO todoDTO) {
		ApplicationTodo todo = UtilsMethods.getResourceByIdOrElseThrow(todoRepository, id, "Todo");
		String newText = todoDTO.getText();
		String currentText = todo.getText();
		if (UtilsMethods.isStringFieldValidForUpdate(newText, currentText)) {
			todo.setText(newText);
			todoRepository.save(todo);
		}
		return mapper.map(todo, ApplicationTodoDTO.class);
	}

	@Override
	public ApplicationTodoDTO deleteById(Long id) {
		ApplicationTodo todo = UtilsMethods.getResourceByIdOrElseThrow(todoRepository, id, "Todo");
		ApplicationTodoDTO deletedTodo = mapper.map(todo, ApplicationTodoDTO.class);
		todoRepository.deleteById(id);
		return deletedTodo;
	}

	@Override
	public ApplicationTodoDTO completeTodo(Long id) {
		ApplicationTodo todo = UtilsMethods.getResourceByIdOrElseThrow(todoRepository, id, "Todo");
		todo.setCompleted(true);
		todoRepository.save(todo);
		return mapper.map(todo, ApplicationTodoDTO.class);
	}
	
	
}
