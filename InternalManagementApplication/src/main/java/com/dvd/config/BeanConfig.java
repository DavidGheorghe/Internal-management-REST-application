package com.dvd.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dvd.jwt.JwtUtils;
import com.dvd.security.JwtAuthenticationEntryPoint;

import lombok.RequiredArgsConstructor;

/**
* Configuration class for beans used in application.
*
* @author David Gheorghe
*/
@RequiredArgsConstructor
@Configuration
public class BeanConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(15);
	}
	
	@Bean
	public JwtUtils jwtConfig() {
		return new JwtUtils();
	}
	
	@Bean
	public JwtAuthenticationEntryPoint authenticationEntryPoint() {
		return new JwtAuthenticationEntryPoint();
	}
}
