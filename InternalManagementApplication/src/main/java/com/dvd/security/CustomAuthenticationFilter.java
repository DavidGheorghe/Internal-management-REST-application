package com.dvd.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.dvd.DTO.UsernameAndPasswordAuthenticationRequest;
import com.dvd.jwt.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
* Defines the custom authentication filter for JWT Authentication.
*
* @author David Gheorghe
*/
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;
	
	@Autowired
	public CustomAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
	}	
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		final String requestUrl = request.getRequestURL().toString();
		String accessToken = jwtUtils.generateAccessToken(authResult, requestUrl);
		String refreshToken = jwtUtils.generateRefreshToken(authResult, requestUrl);
		response.setHeader("Access-Token", "Bearer " + accessToken);
		response.setHeader("Refresh-Token ", "Bearer " + refreshToken);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		try {
			UsernameAndPasswordAuthenticationRequest authenticationRequest = new ObjectMapper()
					.readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);
			Authentication authentication = new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), 
					authenticationRequest.getPassword()
			);
			
			Authentication authenticate = authenticationManager.authenticate(authentication);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			return authenticate;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
