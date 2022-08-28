package com.dvd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dvd.entity.ApplicationRole;
import com.dvd.entity.ApplicationUser;

/**
* Defines the repository for the User resource.
*
* @author David Gheorghe
*/
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
	ApplicationUser findByUsername(String username);
	Boolean existsByUsername(String username);
	List<ApplicationUser> findByRolesContains(ApplicationRole role);
}
