package com.dvd.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dvd.entity.product.ApplicationProduct;

/**
* Defines the repository for the Product resource.
*
* @author David Gheorghe
*/
public interface ApplicationProductRepository extends JpaRepository<ApplicationProduct, Long> {
	Page<ApplicationProduct> findAllByCategoryIdAndNameContains(Long categoryId, String keyword, Pageable pageable);
	Page<ApplicationProduct> findAllByCategoryId(Long categoryId, Pageable pageable);
	@Query(value = "SELECT p FROM ApplicationProduct p WHERE p.category.id IN :categoriesIds")
	Page<ApplicationProduct> findAllByCategoriesIds(@Param("categoriesIds") List<Long> categoriesIds, Pageable pageable);
	Page<ApplicationProduct> findAllByNameContains(String keyword, Pageable pageable);
}
