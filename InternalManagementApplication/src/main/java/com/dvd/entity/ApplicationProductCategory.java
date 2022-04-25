package com.dvd.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.lang.NonNull;

import lombok.Data;

/**
* Defines the ProductCategory resource.
*
* @author David Gheorghe
*/
@Data
@Entity
@Table(name = "ProductCategory", uniqueConstraints = @UniqueConstraint(columnNames = {"categoryName"}))
public class ApplicationProductCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NonNull
	private String categoryName;

	public ApplicationProductCategory() {
	}
}