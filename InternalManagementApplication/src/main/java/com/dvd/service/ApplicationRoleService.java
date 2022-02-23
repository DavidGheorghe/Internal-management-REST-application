package com.dvd.service;

import com.dvd.DTO.ApplicationPrivilegeDTO;
import com.dvd.DTO.ApplicationRoleDTO;
import com.dvd.DTO.GetResourcesResponse;

/**
* Defines the service interface for the Role resource.
*
* @author David Gheorghe
*/
public interface ApplicationRoleService {
	/**
	 * Creates a new role.
	 * 
	 * @param newRoleDTO - DTO representation of the new role.
	 * @return The new role. 
	 */
	ApplicationRoleDTO createRole(ApplicationRoleDTO newRoleDTO);
	
	/**
	 * Returns the role that matches the @param id.
	 * 
	 * @param id - the id of the role that is retrieved.
	 * @return The retrieved role.
	 */
	ApplicationRoleDTO getRoleById(Long id);
	
	/**
	 * Returns the roles. The response is based on pagination and sorting.
	 * Go to {@link com.dvd.utils.ApplicationConstants} class to see the parameters default values.
	 * 
	 * @param pageNo - page number.
	 * @param pageSize - page size.
	 * @param sortBy - criteria for sorting.
	 * @param sortDir - sorting direction  (ASC or DESC).
	 * @return A custom response with the roles.
	 */
	GetResourcesResponse<ApplicationRoleDTO> getAllRoles(int pageNo, int pageSize, String sortBy, String sortDir);
	
	/**
	 * Deletes the role that matches the @param id.
	 * 
	 * @param id - the id of the role that is deleted.
	 * @return The deleted role.
	 */
	ApplicationRoleDTO deleteRoleById(Long id);
	
	/**
	 * Updates fields of a certain role.
	 * 
	 * @param id - the id of the role that is updated.
	 * @param newRoleDTO - DTO representation with the new updates.
	 * @return The updated role. 
	 */
	ApplicationRoleDTO updateRole(Long id, ApplicationRoleDTO newRoleDTO);
	
	/**
	 * Adds new privileges to a certain role.
	 * 
	 * @param id - the id of the role that is updated.
	 * @param privileges - the new privileges.
	 * @return The updated role. 
	 */
	ApplicationRoleDTO addPrivilegesToRole(Long id, ApplicationPrivilegeDTO privileges);
	
	/**
	 * Removes privileges from a certain role.
	 * 
	 * @param id - the id of the role that is updated.
	 * @param privileges - the privileges that will be removed.
	 * @return The updated role.
	 */
	ApplicationRoleDTO removePrivilegesFromRole(Long id, ApplicationPrivilegeDTO privileges);
	
	/**
	 * Removes all privileges from a certain role.
	 * 
	 * @param id - the id of the role that is updated.
	 * @return The updated role.
	 */
	ApplicationRoleDTO removeAllPrivilegesFromRole(Long id);
}
