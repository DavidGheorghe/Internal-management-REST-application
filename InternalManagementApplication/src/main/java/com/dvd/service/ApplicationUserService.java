package com.dvd.service;

import java.security.Principal;
import java.util.List;

import com.dvd.DTO.ApplicationUserDTO;
import com.dvd.DTO.CreateUserDTO;
import com.dvd.DTO.GetResourcesResponse;
import com.dvd.DTO.UpdateUserDTO;
import com.dvd.entity.ApplicationRole;

/**
* Defines the service interface for the User resource.
*
* @author David Gheorghe
*/
public interface ApplicationUserService {
	/**
	 * Creates a new user.
	 * 
	 * @param newUser - DTO object specifically for creating users.
	 * @return The new user.
	 */
	ApplicationUserDTO createUser(CreateUserDTO newUser, Principal principal);
	
	/**
	 * Deletes the user that matches the @param id.
	 * 
	 * @param id - the id of the user that must be deleted.
	 * @return The deleted user.
	 */
	ApplicationUserDTO deleteUserById(Long id);
	
	/**
	 * Returns the users. The response is based on pagination and sorting.
	 * Go to {@link com.dvd.utils.ApplicationConstants} class to see the parameters default values.
	 * 
	 * @param pageNo - page number.
	 * @param pageSize - page size.
	 * @param sortBy - criteria for sorting.
	 * @param sortDir - sorting direction  (ASC or DESC).
	 * @return A custom response with users.
	 */
	GetResourcesResponse<ApplicationUserDTO> getAllUsers(int pageNo, int pageSize, String sortBy, String sortDir);
	
	/**
	 * Returns the user that matches the @param id.
	 * 
	 * @param id - the id of the users that is retrieved.
	 * @return The retrieved user.
	 */
	ApplicationUserDTO getUserById(Long id);
	
	/**
	 * Updates the user's username.
	 * 
	 * @param id - the id of the user that is updated.
	 * @param userDTO - DTO representation of a user with the new username.
	 * @return The updated user.
	 */
	ApplicationUserDTO updateUsername(Long id, UpdateUserDTO userDTO);
	
	/**
	 * Change password method.
	 * 
	 * @param id - the id of the user.
	 * @param oldPassword
	 * @param newPassword
	 * @return The updated user.
	 */
	ApplicationUserDTO changePassword(Long id, String oldPassword, String newPassword);
	
	/**
	 * Adds role(s) to a user.
	 * 
	 * @param id - the id of the user
	 * @param userDTO - DTO representation of a user with the new roles.
	 * @return The updated user.
	 */
	ApplicationUserDTO addRoles(Long id, UpdateUserDTO userDTO);
	
	/**
	 * Removes role(s) from a user.
	 * 
	 * @param id - the id of the user
	 * @param userDTO - DTO representation of a user with the removed roles.
	 * @return The updated user.
	 */
	ApplicationUserDTO removeRoles(Long id, UpdateUserDTO userDTO);
	
	/**
	 * Returns the current user based on the user who made the request.
	 * 
	 * @param username
	 * @return
	 */
	ApplicationUserDTO getCurrentUser(String username);

	List<ApplicationUserDTO> getAllUsers();

	List<ApplicationUserDTO> getAllUsersByRole(ApplicationRole role);
}
