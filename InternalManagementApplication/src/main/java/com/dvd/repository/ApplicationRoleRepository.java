package com.dvd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dvd.entity.ApplicationRole;

/**
* Defines the repository for the Role resource.
*
* @author David Gheorghe
*/
public interface ApplicationRoleRepository extends JpaRepository<ApplicationRole, Long> {
	ApplicationRole findByName(String name);
}
