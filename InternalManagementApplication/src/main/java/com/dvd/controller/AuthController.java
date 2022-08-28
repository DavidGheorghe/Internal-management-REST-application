package com.dvd.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dvd.DTO.ApplicationUserDTO;
import com.dvd.DTO.UsernameAndPasswordAuthenticationRequest;
import com.dvd.entity.ApplicationUser;
import com.dvd.jwt.JwtUtils;
import com.dvd.repository.ApplicationUserRepository;
import com.dvd.service.ApplicationUserService;
import com.dvd.utils.ApplicationConstants;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
* Defines the authentication controller.
*
* @author David Gheorghe
*/
@RequiredArgsConstructor
@RestController
@RequestMapping
public class AuthController {
	private final JwtUtils jwtUtils;
	
	@PostMapping(ApplicationConstants.AUTH_REFRESH_TOKEN)
	public ResponseEntity<String> getRefreshToken(HttpServletRequest request) {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authorizationHeader != null && authorizationHeader.startsWith(ApplicationConstants.JWT_AUTHORIZATION_PREFIX)) {
			String uriStr = ServletUriComponentsBuilder.fromCurrentContextPath().path(ApplicationConstants.AUTH_REFRESH_TOKEN).toUriString();
			
			String refreshToken = authorizationHeader.substring(ApplicationConstants.JWT_AUTHORIZATION_PREFIX.length());
			String username = jwtUtils.getUsernameFromToken(refreshToken);
			String newAccessToken = jwtUtils.generateRefreshTokenFromUsername(username, uriStr); //jwtUtils.generateAccessTokenAfterExpiration(user, uriStr);
			
			HttpHeaders responseHeaders = this.getHeadersWithTokens(newAccessToken, refreshToken);
			
			return ResponseEntity.ok()
					.headers(responseHeaders)
					.body("Refresh token sent successfully!");
		} else {
			throw new RuntimeException("Refresh token is missing.");
		}
	}
	
	private HttpHeaders getHeadersWithTokens(String accessToken, String refreshToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Access-Token", ApplicationConstants.JWT_AUTHORIZATION_PREFIX + accessToken);
		headers.add("Refresh-Token", ApplicationConstants.JWT_AUTHORIZATION_PREFIX + refreshToken);
		return headers;
	}
}
