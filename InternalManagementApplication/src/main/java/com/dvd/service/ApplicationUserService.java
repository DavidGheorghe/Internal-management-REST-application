package com.dvd.service;

import com.dvd.DTO.ApplicationUserDTO;
import com.dvd.DTO.CreateUserDTO;
import com.dvd.DTO.GetResourcesResponse;

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
	ApplicationUserDTO createUser(CreateUserDTO newUser);
	
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
	ApplicationUserDTO updateUsername(Long id, ApplicationUserDTO userDTO);
	
	/**
	 * Adds privileges to a certain user.
	 * 
	 * @param id - the id of the user that is updated.
	 * @param userDTO - DTO representation of a user with the new privileges.
	 * @return The updated user.
	 */
	ApplicationUserDTO addPrivileges(Long id, ApplicationUserDTO userDTO);
	
	/**
	 * Removes privileges from a certain user.
	 * 
	 * @param id - the id of the user that is updated.
	 * @param userDTO - DTO representation of a user with the privileges that will be removed.
	 * @return The updated user.
	 */
	ApplicationUserDTO removePrivileges(Long id, ApplicationUserDTO userDTO);
}
