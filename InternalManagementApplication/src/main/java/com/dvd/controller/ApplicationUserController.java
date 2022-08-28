package com.dvd.controller;

import static org.springframework.http.HttpStatus.OK;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dvd.DTO.ApplicationUserDTO;
import com.dvd.DTO.CreateUserDTO;
import com.dvd.DTO.GetResourcesResponse;
import com.dvd.DTO.UpdateUserDTO;
import com.dvd.entity.ApplicationRole;
import com.dvd.service.ApplicationUserService;
import com.dvd.utils.ApplicationConstants;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
* Defines the controller for the User resource.
*
* @author David Gheorghe
*/
@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping(ApplicationConstants.ADMIN_API_USER_ROOT)
//@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ApplicationUserController {
	private final ApplicationUserService userService;
	
	@PostMapping
	public ResponseEntity<ApplicationUserDTO> createUser(@Valid @RequestBody CreateUserDTO createUserDTO, Principal principal) {
		ApplicationUserDTO createdUser = userService.createUser(createUserDTO);
		if (log.isInfoEnabled()) {
			log.info("User " + principal.getName() + " created new user '" + createdUser.getUsername() + "'.");
		}
		return new ResponseEntity<ApplicationUserDTO>(createdUser, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<GetResourcesResponse<ApplicationUserDTO>> getAllUsers(
			@RequestParam(value = "pageNo", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = ApplicationConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = ApplicationConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
		return new ResponseEntity<GetResourcesResponse<ApplicationUserDTO>>(userService.getAllUsers(pageNo, pageSize, sortBy, sortDir), OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApplicationUserDTO> getUserById(@Min(0) @PathVariable Long id) {
		ApplicationUserDTO user = userService.getUserById(id);
		return new ResponseEntity<ApplicationUserDTO>(user, HttpStatus.OK);
	}
	
	@GetMapping("/employees")
	public ResponseEntity<List<ApplicationUserDTO>> getAllEmployees() {
		List<ApplicationUserDTO> employees = userService.getAllUsersByRole(ApplicationRole.EMPLOYEE);
		return new ResponseEntity<List<ApplicationUserDTO>>(employees, OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApplicationUserDTO> updateUsername(@Min(0) @PathVariable Long id, @Valid @RequestBody UpdateUserDTO userDTO) {
		ApplicationUserDTO updatedUser = userService.updateUsername(id, userDTO);
		return new ResponseEntity<ApplicationUserDTO>(updatedUser, OK);
	}
	
	@PutMapping("/{id}/add-roles")
	public ResponseEntity<ApplicationUserDTO> addRoles(@Min(0) @PathVariable Long id, @Valid @RequestBody UpdateUserDTO userDTO, Principal principal) {
		ApplicationUserDTO updatedUser = userService.addRoles(id, userDTO);
		return new ResponseEntity<ApplicationUserDTO>(updatedUser, OK);
	}
	
	@PutMapping("/{id}/remove-roles")
	public ResponseEntity<ApplicationUserDTO> removeRoles(@Min(0) @PathVariable Long id, @Valid @RequestBody UpdateUserDTO userDTO, Principal principal) {
		ApplicationUserDTO updatedUser = userService.removeRoles(id, userDTO);
		return new ResponseEntity<ApplicationUserDTO>(updatedUser, OK);
	}
	
	@DeleteMapping("/{username}")
	public ResponseEntity<ApplicationUserDTO> deleteUserByUsername(@PathVariable String username, Principal principal) {
		ApplicationUserDTO deletedUser = userService.deleteUserByUsername(username);
		if (log.isInfoEnabled()) {
			log.info("User " + principal.getName() + " deleted user '" + deletedUser.getUsername() + "'.");
		}
		return new ResponseEntity<ApplicationUserDTO>(deletedUser, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<ApplicationUserDTO>> getAllUsers() {
		List<ApplicationUserDTO> usersDTO = userService.getAllUsers();
		return new ResponseEntity<List<ApplicationUserDTO>>(usersDTO, OK);
	}
}
