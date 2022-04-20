package com.dvd.controller;

import static org.springframework.http.HttpStatus.OK;

import java.security.Principal;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
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
import com.dvd.entity.ApplicationPrivilege;
import com.dvd.service.ApplicationRoleService;
import com.dvd.utils.ApplicationConstants;

import lombok.extern.log4j.Log4j2;

/**
* Defines the controller for the Role resource.
*
* @author David Gheorghe
*/
@Log4j2
@Validated
@RestController
@RequestMapping(ApplicationConstants.ADMIN_API_ROLE_ROOT)
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ApplicationRoleController {
	
	private final ApplicationRoleService roleService;
	
	@Autowired
	public ApplicationRoleController(ApplicationRoleService roleService) {
		this.roleService = roleService;
	}

	@PostMapping
	public ResponseEntity<ApplicationRoleDTO> createRole(@Valid @RequestBody ApplicationRoleDTO newRoleDTO, Principal principal) {
		ApplicationRoleDTO addedRole = roleService.createRole(newRoleDTO);
		if (log.isInfoEnabled()) {
			log.info("User " + principal.getName() + " created new role. Name: " + addedRole.getName() + ", privileges: " + getPrivilegesAsString(addedRole.getPrivileges()));
		}
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
	public ResponseEntity<ApplicationRoleDTO> getRoleById(@Min(0) @PathVariable Long id) {
		ApplicationRoleDTO role = roleService.getRoleById(id);
		return new ResponseEntity<ApplicationRoleDTO>(role, OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApplicationRoleDTO> deleteRoleById(@Min(0) @PathVariable Long id, Principal principal) {
		ApplicationRoleDTO deletedRole = roleService.deleteRoleById(id);
		if (log.isInfoEnabled()) {
			log.info("User " + principal.getName() + " deleted role " + deletedRole.getName() + ".");
		}		
		return new ResponseEntity<ApplicationRoleDTO>(deletedRole, OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApplicationRoleDTO> updateRole(@Min(0) @PathVariable Long id, @Valid @RequestBody ApplicationRoleDTO roleDTO, Principal principal) {
		ApplicationRoleDTO updatedRole = roleService.updateRole(id, roleDTO);
		if (log.isInfoEnabled()) {
			log.info("User " + principal.getName() + " updated '" + updatedRole.getName() + "' role. Name: " + updatedRole.getName() + ", privileges: " + getPrivilegesAsString(roleDTO.getPrivileges()));
		}
		return new ResponseEntity<ApplicationRoleDTO>(updatedRole, OK);
	}
	
	@PutMapping("/{id}/addPrivileges")
	public ResponseEntity<ApplicationRoleDTO> addPrivilegesToRole(@Min(0) @PathVariable Long id, @Valid @RequestBody ApplicationPrivilegeDTO privilegeDTO, Principal principal) {
		ApplicationRoleDTO updatedRole = roleService.addPrivilegesToRole(id, privilegeDTO);
		return new ResponseEntity<ApplicationRoleDTO>(updatedRole, OK);
	}
	
	@PutMapping("/{id}/removePrivileges")
	public ResponseEntity<ApplicationRoleDTO> removePrivilegesFromRole(@Min(0) @PathVariable Long id, @Valid @RequestBody ApplicationPrivilegeDTO privilegeDTO) {
		ApplicationRoleDTO updatedRole = roleService.removePrivilegesFromRole(id, privilegeDTO);
		return new ResponseEntity<ApplicationRoleDTO>(updatedRole, OK);
	}
	
	@PutMapping("/{id}/removeAllPrivileges")
	public ResponseEntity<ApplicationRoleDTO> removeAllPrivilegesFromRole(@Min(0) @PathVariable Long id) {
		ApplicationRoleDTO updatedRole = roleService.removeAllPrivilegesFromRole(id);
		return new ResponseEntity<ApplicationRoleDTO>(updatedRole, OK);
	}
	
	private String getPrivilegesAsString(Set<ApplicationPrivilege> privileges) {
		StringBuilder privilegesStr = new StringBuilder();
		if (privileges != null) {
			privileges.forEach(privilege -> privilegesStr.append(privilege.name() + "; "));
		}		
		return privileges.toString();
	}
}
