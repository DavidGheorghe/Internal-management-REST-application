package com.dvd;

import java.util.HashSet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.dvd.DTO.ApplicationProductCategoryDTO;
import com.dvd.DTO.CreateUserDTO;
import com.dvd.service.ApplicationProductCategoryService;
import com.dvd.service.ApplicationUserService;

import lombok.RequiredArgsConstructor;

/**
* Defines the command runner class which runs every time the server starts.
*
* @author David Gheorghe
*/
@Component
@RequiredArgsConstructor
public class CommandRunner implements CommandLineRunner {
	
	private final ApplicationProductCategoryService productCategoryService;
	private final ApplicationUserService userService;
	
	@Override
	public void run(String... args) throws Exception {
		createCategories();
		createAdmin();
	}

	private void createAdmin() {
		final String username = "admin";
		if (userService.existsByUsername(username) == false) {
			HashSet<Integer> rolesIds = new HashSet<>();
			rolesIds.add(3);
			CreateUserDTO admin = new CreateUserDTO(username, rolesIds);
			userService.createUser(admin);
		}
	}

	private void createCategories() {
		if (productCategoryService.getCategoriesNames().isEmpty()) {
			ApplicationProductCategoryDTO firstCategory = new ApplicationProductCategoryDTO();
			firstCategory.setCategoryName("Flower Pot");
			productCategoryService.createProductCategory(firstCategory);
			
			ApplicationProductCategoryDTO secondCategory = new ApplicationProductCategoryDTO();
			secondCategory.setCategoryName("Candle Pot");
			productCategoryService.createProductCategory(secondCategory);
			
			ApplicationProductCategoryDTO thirdCategory = new ApplicationProductCategoryDTO();
			thirdCategory.setCategoryName("Cup Coaster");
			productCategoryService.createProductCategory(thirdCategory);
		}
	}
	
}
