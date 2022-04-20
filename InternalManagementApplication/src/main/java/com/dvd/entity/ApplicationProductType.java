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
* Defines the ProductType resource.
*
* @author David Gheorghe
*/
@Data
@Entity
@Table(name = "ProductType", uniqueConstraints = @UniqueConstraint(columnNames = {"typeName"}))
public class ApplicationProductType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NonNull
	private String typeName;

	public ApplicationProductType() {
	}
}