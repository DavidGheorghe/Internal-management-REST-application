package com.dvd.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dvd.entity.ApplicationColor;

/**
* Defines the repository for the Color resource.
*
* @author David Gheorghe
*/
public interface ApplicationColorRepository extends JpaRepository<ApplicationColor, Long> {
	Page<ApplicationColor> findAllByNameContains(String keyword, Pageable pageable);
	@Query("SELECT c FROM ApplicationColor c where c.secondPigmentPercentage = NULL AND c.thirdPigmentPercentage = NULL AND c.name LIKE '%' || ?1 || '%'")
	Page<ApplicationColor> findAllByFirstPigmentFilteredBy(String pattern, Pageable pageable);
	@Query("SELECT c FROM ApplicationColor c where c.secondPigmentPercentage <> NULL AND c.thirdPigmentPercentage = NULL AND c.name LIKE '%' || ?1 || '%'")
	Page<ApplicationColor> findAllByFirstAndSecondPigmentFilteredBy(String pattern, Pageable pageable);
	@Query("SELECT c FROM ApplicationColor c where c.secondPigmentPercentage <> NULL AND c.thirdPigmentPercentage <> NULL AND c.name LIKE '%' || ?1 || '%'")
	Page<ApplicationColor> findAllByAllPigmentsFilteredBy(String pattern, Pageable pageable);

	Page<ApplicationColor> findAll(Pageable pageable);
	@Query("SELECT c FROM ApplicationColor c where c.secondPigmentPercentage = NULL AND c.thirdPigmentPercentage = NULL")
	Page<ApplicationColor> findAllByFirstPigment(Pageable pageable);
	@Query("SELECT c FROM ApplicationColor c where c.secondPigmentPercentage <> NULL AND c.thirdPigmentPercentage = NULL")
	Page<ApplicationColor> findAllByFirstAndSecondPigment(Pageable pageable);
	@Query("SELECT c FROM ApplicationColor c where c.secondPigmentPercentage <> NULL AND c.thirdPigmentPercentage <> NULL")
	Page<ApplicationColor> findAllByAllPigments(Pageable pageable);
}