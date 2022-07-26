package com.dvd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dvd.entity.ApplicationTodo;

/**
* Define the repository for the Todo resource.
*
* @author David Gheorghe
*/
public interface ApplicationTodoRepository extends JpaRepository<ApplicationTodo, Long> {
	
}
