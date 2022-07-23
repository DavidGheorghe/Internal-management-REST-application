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
@CrossOrigin(ApplicationConstants.CLIENT_SIDE_URL)
@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping
public class AuthController {
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;
	private final ApplicationUserRepository userRepository;
	private final ApplicationUserService userService;
	
	@PostMapping(ApplicationConstants.AUTH_LOGIN)
	public ResponseEntity<ApplicationUserDTO> logIn(@Valid @RequestBody UsernameAndPasswordAuthenticationRequest request) {
		String username = request.getUsername();
		String password = request.getPassword();
		UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication = authenticationManager.authenticate(user);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String uriStr = ServletUriComponentsBuilder.fromCurrentContextPath().path(ApplicationConstants.AUTH_LOGIN).toUriString();
		
		String accessToken = jwtUtils.generateAccessToken(authentication, uriStr);
		String refreshToken = jwtUtils.generateRefreshToken(authentication, uriStr);
		
		HttpHeaders responseHeaders = this.getHeadersWithTokens(accessToken, refreshToken);
		ApplicationUserDTO currentUser = userService.getCurrentUser(username);
		
		if (log.isInfoEnabled()) {
			log.info("User '" + username + "' authenticated successfully.");
		}
		return ResponseEntity.ok()
				.headers(responseHeaders)
				.body(currentUser);
	}
	
	@PostMapping(ApplicationConstants.AUTH_REFRESH_TOKEN)
	public ResponseEntity<String> getRefreshToken(HttpServletRequest request) {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authorizationHeader != null && authorizationHeader.startsWith(ApplicationConstants.JWT_AUTHORIZATION_PREFIX)) {
			String uriStr = ServletUriComponentsBuilder.fromCurrentContextPath().path(ApplicationConstants.AUTH_REFRESH_TOKEN).toUriString();
			
			String refreshToken = authorizationHeader.substring(ApplicationConstants.JWT_AUTHORIZATION_PREFIX.length());
			String username = jwtUtils.getUsernameFromToken(refreshToken);
//			ApplicationUser user = userRepository.findByUsername(username).get();
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
