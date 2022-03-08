package com.dvd.DTO;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
* TODO: comment
*
* @author David Gheorghe
*/
@Data
public class ChangePasswordDTO {
	@NotBlank
	private String oldPassword;
	@NotBlank
	private String newPassword;
}
