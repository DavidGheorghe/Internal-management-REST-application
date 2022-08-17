package com.dvd.DTO;

import lombok.Data;

/**
* Defines the DTO for changing a password.
*
* @author David Gheorghe
*/
@Data
public class ChangePasswordDTO {
	private String oldPassword;
	private String newPassword;
}
