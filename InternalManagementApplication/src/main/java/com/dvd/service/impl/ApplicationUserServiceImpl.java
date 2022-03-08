package com.dvd.service.impl;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dvd.DTO.ApplicationUserDTO;
import com.dvd.DTO.CreateUserDTO;
import com.dvd.DTO.GetResourcesResponse;
import com.dvd.DTO.UpdateUserDTO;
import com.dvd.entity.ApplicationPrivilege;
import com.dvd.entity.ApplicationRole;
import com.dvd.entity.ApplicationUser;
import com.dvd.exception.RemovePrivilegeFromUserException;
import com.dvd.exception.ResourceNotFoundException;
import com.dvd.exception.SamePasswordException;
import com.dvd.exception.SameUsernameException;
import com.dvd.exception.UsernameTakenException;
import com.dvd.repository.ApplicationRoleRepository;
import com.dvd.repository.ApplicationUserRepository;
import com.dvd.service.ApplicationUserService;

import lombok.RequiredArgsConstructor;

/**
* Defines the implementation of the service interface.
*
* @author David Gheorghe
*/
@RequiredArgsConstructor
@Service
public class ApplicationUserServiceImpl implements ApplicationUserService {

	private final ApplicationUserRepository userRepository; 
	private final ApplicationRoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper mapper;
	
	@Override
	public ApplicationUserDTO createUser(CreateUserDTO createUserDTO, Principal principal) {
		String username = createUserDTO.getUsername();
		if (userRepository.existsByUsername(username)) {
			throw new UsernameTakenException(username);
		}
		String password = passwordEncoder.encode(createUserDTO.getPassword());
		Set<Long> rolesIds = createUserDTO.getRolesIds();
		ApplicationUser newUser = new ApplicationUser(username, password, getRolesFromIds(rolesIds));
		userRepository.save(newUser);
		return mapper.map(newUser, ApplicationUserDTO.class);
	}

	@Override
	public ApplicationUserDTO deleteUserById(Long id) {
		ApplicationUser user = getUserByIdOrElseThrow(id);
		ApplicationUserDTO deletedUser = mapper.map(user, ApplicationUserDTO.class);
		userRepository.deleteById(id);
		return deletedUser;
	}

	@Override
	public GetResourcesResponse<ApplicationUserDTO> getAllUsers(int pageNo, int pageSize, String sortBy,
			String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<ApplicationUser> users = userRepository.findAll(pageable);
		List<ApplicationUser> listOfUsers = users.getContent();
		List<ApplicationUserDTO> content = listOfUsers
												.stream()
												.map(user -> mapper.map(user, ApplicationUserDTO.class))
												.collect(Collectors.toList());
		
		GetResourcesResponse<ApplicationUserDTO> response = new GetResourcesResponse<ApplicationUserDTO>();
		response.setGetResourcesResponseFields(content, users);
		return response;
	}

	@Override
	public ApplicationUserDTO getUserById(Long id) {
		ApplicationUser user = getUserByIdOrElseThrow(id);
		return mapper.map(user, ApplicationUserDTO.class);
	}

	@Override
	public ApplicationUserDTO updateUsername(Long id, UpdateUserDTO userDTO) {
		ApplicationUser user = getUserByIdOrElseThrow(id);
		String newUsername = userDTO.getUsername();
		if (newUsername != null) {
			this.changeUsername(user, newUsername);
		}
		userRepository.save(user);
		return mapper.map(user, ApplicationUserDTO.class);
	}

	@Override
	public ApplicationUserDTO addPrivileges(Long id, UpdateUserDTO userDTO) {
		ApplicationUser user = getUserByIdOrElseThrow(id);
		if (userDTO.getPrivileges() != null) {
			user.getPrivileges().addAll(userDTO.getPrivileges());
		}
		userRepository.save(user);
		return mapper.map(user, ApplicationUserDTO.class);
	}

	@Override
	public ApplicationUserDTO removePrivileges(Long id, UpdateUserDTO userDTO) {
		ApplicationUser user = getUserByIdOrElseThrow(id);
		if (userDTO.getPrivileges() != null) {
			for (ApplicationPrivilege newPrivilege: userDTO.getPrivileges()) {
				if (rolesContainsPrivilege(user.getRoles(), newPrivilege)) {
					throw new RemovePrivilegeFromUserException(newPrivilege.name(), user.getUsername());
				} else {
					user.getPrivileges().remove(newPrivilege);
				}
			}			
		}
		userRepository.save(user);
		return mapper.map(user, ApplicationUserDTO.class);
	}
	
	@Override
	public ApplicationUserDTO addRoles(Long id, UpdateUserDTO userDTO) {
		ApplicationUser user = getUserByIdOrElseThrow(id);
		Set<Long> rolesIds = userDTO.getRolesIds();
		user.getRoles().addAll(getRolesFromIds(rolesIds));
		userRepository.save(user);
		return mapper.map(user, ApplicationUserDTO.class);
	}

	@Override
	public ApplicationUserDTO removeRoles(Long id, UpdateUserDTO userDTO) {
		ApplicationUser user = getUserByIdOrElseThrow(id);
		Set<Long> rolesIds = userDTO.getRolesIds();
		user.getRoles().removeAll(getRolesFromIds(rolesIds));
		userRepository.save(user);
		return mapper.map(user, ApplicationUserDTO.class);
	}
	
	@Override
	public ApplicationUserDTO changePassword(Long id, String oldPassword, String newPassword) {
		ApplicationUser user = getUserByIdOrElseThrow(id);
		String existingPassword = user.getPassword();
		if (oldPassword.equals(newPassword)) {
			throw new SamePasswordException("New password is the same as old password.");
		}
		if (!passwordEncoder.matches(oldPassword, existingPassword)) {
			throw new RuntimeException("Old password doesn't match with the current password");
		}		
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
		return mapper.map(user, ApplicationUserDTO.class);
	}
	
	private Set<ApplicationRole> getRolesFromIds(Set<Long> ids) {
		List<ApplicationRole> roles = roleRepository.findAllById(ids);
		return new HashSet<ApplicationRole>(roles);
	}
	
	private ApplicationUser getUserByIdOrElseThrow(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", String.valueOf(id)));
	}
	
	private void changeUsername(ApplicationUser updatedUser, String newUsername) {
		String currentUsername = updatedUser.getUsername();
		if (currentUsername.equals(newUsername)) {
			throw new SameUsernameException(currentUsername);
		}
		if (userRepository.existsByUsername(newUsername)) {
			throw new UsernameTakenException(newUsername);
		}
		updatedUser.setUsername(newUsername);
	}
	
	private boolean rolesContainsPrivilege(Set<ApplicationRole> roles, ApplicationPrivilege privilege) {		
		boolean contains = false;
		for (ApplicationRole role: roles) {
			if (role.hasPrivilege(privilege)) {
				contains = true;
				break;
			}
		}
		return contains;
	}
}
