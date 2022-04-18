package com.dvd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dvd.entity.ApplicationColor;

/**
* Defines the repository for the Color resource.
*
* @author David Gheorghe
*/
public interface ApplicationColorRepository extends JpaRepository<ApplicationColor, Long> {

}