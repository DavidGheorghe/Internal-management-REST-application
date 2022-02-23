package com.dvd.controller;

import static org.springframework.http.HttpStatus.OK;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.dvd.DTO.ApplicationPrivilegeDTO;
import com.dvd.DTO.ApplicationRoleDTO;
import com.dvd.DTO.GetResourcesResponse;
import com.dvd.service.ApplicationRoleService;
import com.dvd.utils.ApplicationConstants;

/**
* Defines the controller for the Role resource.
*
* @author David Gheorghe
*/
@RestController
@RequestMapping("/admin/api/role")
public class ApplicationRoleController {
	
	private final ApplicationRoleService roleService;
	
	@Autowired
	public ApplicationRoleController(ApplicationRoleService roleService) {
		this.roleService = roleService;
	}

	@PostMapping
	public ResponseEntity<ApplicationRoleDTO> createRole(@RequestBody ApplicationRoleDTO newRoleDTO) {
		ApplicationRoleDTO addedRole = roleService.createRole(newRoleDTO);
		return new ResponseEntity<ApplicationRoleDTO>(addedRole, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<GetResourcesResponse<ApplicationRoleDTO>> getAllRoles(
			@RequestParam(value = "pageNo", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = ApplicationConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = ApplicationConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
		return new ResponseEntity<GetResourcesResponse<ApplicationRoleDTO>>(roleService.getAllRoles(pageNo, pageSize, sortBy, sortDir), OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApplicationRoleDTO> getRoleById(@PathVariable Long id) {
		ApplicationRoleDTO role = roleService.getRoleById(id);
		return new ResponseEntity<ApplicationRoleDTO>(role, OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApplicationRoleDTO> deleteRoleById(@PathVariable Long id) {
		ApplicationRoleDTO deletedRole = roleService.deleteRoleById(id);
		return new ResponseEntity<ApplicationRoleDTO>(deletedRole, OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApplicationRoleDTO> updateRole(@PathVariable Long id, @RequestBody ApplicationRoleDTO roleDTO) {
		ApplicationRoleDTO updatedRole = roleService.updateRole(id, roleDTO);
		return new ResponseEntity<ApplicationRoleDTO>(updatedRole, OK);
	}
	
	@PutMapping("/{id}/addPrivileges")
	public ResponseEntity<ApplicationRoleDTO> addPrivilegesToRole(@PathVariable Long id, @RequestBody ApplicationPrivilegeDTO privilegeDTO) {
		ApplicationRoleDTO updatedRole = roleService.addPrivilegesToRole(id, privilegeDTO);
		return new ResponseEntity<ApplicationRoleDTO>(updatedRole, OK);
	}
	
	@PutMapping("/{id}/removePrivileges")
	public ResponseEntity<ApplicationRoleDTO> removePrivilegesFromRole(@PathVariable Long id, @RequestBody ApplicationPrivilegeDTO privilegeDTO) {
		ApplicationRoleDTO updatedRole = roleService.removePrivilegesFromRole(id, privilegeDTO);
		return new ResponseEntity<ApplicationRoleDTO>(updatedRole, OK);
	}
	
	@PutMapping("/{id}/removeAllPrivileges")
	public ResponseEntity<ApplicationRoleDTO> removeAllPrivilegesFromRole(@PathVariable Long id) {
		ApplicationRoleDTO updatedRole = roleService.removeAllPrivilegesFromRole(id);
		return new ResponseEntity<ApplicationRoleDTO>(updatedRole, OK);
	}
}
