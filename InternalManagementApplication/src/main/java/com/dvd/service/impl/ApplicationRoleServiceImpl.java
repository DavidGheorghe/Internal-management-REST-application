package com.dvd.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dvd.DTO.ApplicationPrivilegeDTO;
import com.dvd.DTO.ApplicationRoleDTO;
import com.dvd.DTO.GetResourcesResponse;
import com.dvd.entity.ApplicationRole;
import com.dvd.exception.ResourceNotFoundException;
import com.dvd.repository.ApplicationRoleRepository;
import com.dvd.service.ApplicationRoleService;

import lombok.RequiredArgsConstructor;

/**
* Defines the implementation of the service interface.
*
* @author David Gheorghe
*/
@RequiredArgsConstructor
@Service
public class ApplicationRoleServiceImpl implements ApplicationRoleService {
	
	private final ModelMapper mapper;
	private final ApplicationRoleRepository roleRepository;
	
	/**
	 *
	 */
	@Override
	public ApplicationRoleDTO createRole(ApplicationRoleDTO newRoleDTO) {
		ApplicationRole role = mapper.map(newRoleDTO, ApplicationRole.class);
		ApplicationRole newRole = roleRepository.save(role);
		return mapper.map(newRole, ApplicationRoleDTO.class);
	}
	
	@Override
	public ApplicationRoleDTO getRoleById(Long id) {
		ApplicationRole role = getRoleOrElseThrow(id);
		return mapper.map(role, ApplicationRoleDTO.class);
	}
	
	
	@Override
	public GetResourcesResponse<ApplicationRoleDTO> getAllRoles(int pageNo, int pageSize, String sortBy,
			String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<ApplicationRole> roles = roleRepository.findAll(pageable);
		List<ApplicationRole> listOfRoles = roles.getContent();
		List<ApplicationRoleDTO> content = listOfRoles
												.stream()
												.map(role -> mapper.map(role, ApplicationRoleDTO.class))
												.collect(Collectors.toList());
		GetResourcesResponse<ApplicationRoleDTO> response = new GetResourcesResponse<ApplicationRoleDTO>();
		response.setGetResourcesResponseFields(content, roles);
		return response;
	}
	
	@Override
	public ApplicationRoleDTO deleteRoleById(Long id) {
		ApplicationRole role = getRoleOrElseThrow(id);
		ApplicationRoleDTO deletedRole = mapper.map(role, ApplicationRoleDTO.class);
		roleRepository.deleteById(id);
		return deletedRole;
	}

	@Override
	public ApplicationRoleDTO updateRole(Long id, ApplicationRoleDTO roleDTO) {
		ApplicationRole role = getRoleOrElseThrow(id);
		if (roleDTO.getName() != null && !role.getName().equals(roleDTO.getName())) {
			role.setName(roleDTO.getName());
		}
		if (roleDTO.getPrivileges() != null && !roleDTO.getPrivileges().isEmpty()) {
			role.setPrivileges(roleDTO.getPrivileges());
		}		
		roleRepository.save(role);
		return mapper.map(role, ApplicationRoleDTO.class);
	}

	@Override
	public ApplicationRoleDTO addPrivilegesToRole(Long roleId, ApplicationPrivilegeDTO privilegesDTO) {
		ApplicationRole role = getRoleOrElseThrow(roleId);
		if (privilegesDTO.getPrivileges() != null) {
			role.getPrivileges().addAll(privilegesDTO.getPrivileges());
		}
		roleRepository.save(role);
		return mapper.map(role, ApplicationRoleDTO.class);
	}

	@Override
	public ApplicationRoleDTO removePrivilegesFromRole(Long roleId, ApplicationPrivilegeDTO privilegesDTO) {
		ApplicationRole role = getRoleOrElseThrow(roleId);
		if (privilegesDTO.getPrivileges() != null) {
			role.getPrivileges().removeAll(privilegesDTO.getPrivileges());
		}
		roleRepository.save(role);
		return mapper.map(role, ApplicationRoleDTO.class);
	}

	@Override
	public ApplicationRoleDTO removeAllPrivilegesFromRole(Long roleId) {
		ApplicationRole role = getRoleOrElseThrow(roleId);		
		role.getPrivileges().clear();
		roleRepository.save(role);
		return mapper.map(role, ApplicationRoleDTO.class);
	}
	
	private ApplicationRole getRoleOrElseThrow(Long id) {
		return roleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Role", "id", String.valueOf(id)));
	}

	
	
	
}
