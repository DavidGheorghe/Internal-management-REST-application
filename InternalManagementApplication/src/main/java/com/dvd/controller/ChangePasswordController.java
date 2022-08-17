package com.dvd.controller;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dvd.DTO.ApplicationUserDTO;
import com.dvd.DTO.ChangePasswordDTO;
import com.dvd.service.ApplicationUserService;
import com.dvd.utils.ApplicationConstants;

import lombok.RequiredArgsConstructor;

/**
* Defines the controller used to change password.
*
* @author David Gheorghe
*/
@RequiredArgsConstructor
@RestController
@RequestMapping
public class ChangePasswordController {
	
	private final ApplicationUserService userService;
	
	@PutMapping(ApplicationConstants.API_CHANGE_PASSWORD + "/{id}")
	public ResponseEntity<ApplicationUserDTO> changePassword(@PathVariable Long id, @RequestBody ChangePasswordDTO changePasswordDTO) {
		String oldPassword = changePasswordDTO.getOldPassword();
		String newPassword = changePasswordDTO.getNewPassword();
		ApplicationUserDTO userDTO = userService.changePassword(id, oldPassword, newPassword);
		return new ResponseEntity<ApplicationUserDTO>(userDTO, HttpStatus.OK);
	}
}
