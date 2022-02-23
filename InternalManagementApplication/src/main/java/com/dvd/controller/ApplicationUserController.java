package com.dvd.controller;

import static org.springframework.http.HttpStatus.OK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.dvd.service.ApplicationUserService;
import com.dvd.utils.ApplicationConstants;

/**
* Defines the controller for the User resource.
*
* @author David Gheorghe
*/
@RestController
@RequestMapping("/admin/api/user")
@PreAuthorize("hasRole('ADMIN')")
public class ApplicationUserController {
	private final ApplicationUserService userService;

	@Autowired
	public ApplicationUserController(ApplicationUserService userService) {
		this.userService = userService;
	}
	
	@PostMapping
	public ResponseEntity<ApplicationUserDTO> createUser(@RequestBody CreateUserDTO createUserDTO) {
		ApplicationUserDTO createdUser = userService.createUser(createUserDTO);
		return new ResponseEntity<ApplicationUserDTO>(createdUser, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<GetResourcesResponse<ApplicationUserDTO>> getAllRoles(
			@RequestParam(value = "pageNo", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = ApplicationConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = ApplicationConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
		return new ResponseEntity<GetResourcesResponse<ApplicationUserDTO>>(userService.getAllUsers(pageNo, pageSize, sortBy, sortDir), OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApplicationUserDTO> getUserById(@PathVariable Long id) {
		ApplicationUserDTO user = userService.getUserById(id);
		return new ResponseEntity<ApplicationUserDTO>(user, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApplicationUserDTO> updateUsername(@PathVariable Long id, @RequestBody ApplicationUserDTO userDTO) {
		ApplicationUserDTO updatedUser = userService.updateUsername(id, userDTO);
		return new ResponseEntity<ApplicationUserDTO>(updatedUser, OK);
	}
	
	@PutMapping("/{id}/addPrivileges")
	public ResponseEntity<ApplicationUserDTO> addPrivileges(@PathVariable Long id, @RequestBody ApplicationUserDTO userDTO) {
		ApplicationUserDTO updatedUser = userService.addPrivileges(id, userDTO);
		return new ResponseEntity<ApplicationUserDTO>(updatedUser, OK);
	}
	
	@PutMapping("/{id}/removePrivileges")
	public ResponseEntity<ApplicationUserDTO> removePrivileges(@PathVariable Long id, @RequestBody ApplicationUserDTO userDTO) {
		ApplicationUserDTO updatedUser = userService.removePrivileges(id, userDTO);
		return new ResponseEntity<ApplicationUserDTO>(updatedUser, OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApplicationUserDTO> deleteUserById(@PathVariable Long id) {
		ApplicationUserDTO deletedUser = userService.deleteUserById(id);
		return new ResponseEntity<ApplicationUserDTO>(deletedUser, HttpStatus.OK);
	}
}
