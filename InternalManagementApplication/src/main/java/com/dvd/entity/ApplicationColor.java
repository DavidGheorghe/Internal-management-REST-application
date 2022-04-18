package com.dvd.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import lombok.Data;

/**
 * Defines the Color resource.
 *
 * @author David Gheorghe
 */
@Data
@Entity
@Table(name = "Color")
public class ApplicationColor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NonNull
	private String name;
	@NonNull
	private String firstPigment;
	@NonNull
	private Double firstPigmentPercentage;
	@Nullable
	private String secondPigment;
	@Nullable
	private Double secondPigmentPercentage;
	@Nullable
	private String thirdPigment;
	@Nullable
	private Double thirdPigmentPercentage;

	public ApplicationColor() {

	}
	
	public ApplicationColor(String name, String firstPigment, Double firstPigmentPercentage) {
		this.name = name;
		this.firstPigment = firstPigment;
		this.firstPigmentPercentage = firstPigmentPercentage;
	}
}