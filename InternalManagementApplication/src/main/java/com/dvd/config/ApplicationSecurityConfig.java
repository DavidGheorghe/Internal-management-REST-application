package com.dvd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.dvd.jwt.JwtUtils;
import com.dvd.security.CustomAuthenticationFilter;
import com.dvd.security.CustomAuthorizationFilter;
import com.dvd.security.CustomUserDetailsService;
import com.dvd.security.JwtAuthenticationEntryPoint;

import lombok.RequiredArgsConstructor;

/**
* Configuration class for security.
*
* @author David Gheorghe
*/
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final CustomUserDetailsService customUserDetailsService;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtils jwtConfig;
	private final JwtAuthenticationEntryPoint authenticationEntryPoint;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager(), jwtConfig);
		CustomAuthorizationFilter customAuthorizationFilter = new CustomAuthorizationFilter(jwtConfig);
		http
			.csrf().disable()
			.exceptionHandling()
			.authenticationEntryPoint(authenticationEntryPoint)
			.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.addFilter(customAuthenticationFilter)
				.addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests().antMatchers("/api/auth/login/**", "/api/auth/refreshToken").permitAll()
			.and()
				.authorizeRequests().anyRequest()
			.authenticated();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
	}
	
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
}
