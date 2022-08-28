package com.dvd.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.dvd.jwt.JwtUtils;
import com.dvd.security.CustomAuthenticationFilter;
import com.dvd.security.CustomAuthorizationFilter;
import com.dvd.security.CustomUserDetailsService;
import com.dvd.security.JwtAccessDeniedHandler;
import com.dvd.security.JwtAuthenticationEntryPoint;
import com.dvd.utils.ApplicationConstants;

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
	private final JwtAccessDeniedHandler accessDeniedHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager(), jwtConfig);
		CustomAuthorizationFilter customAuthorizationFilter = new CustomAuthorizationFilter(jwtConfig);
		customAuthenticationFilter.setFilterProcessesUrl(ApplicationConstants.AUTH_LOGIN);
		http
			.cors()
			.and()
			.csrf().disable()
			.exceptionHandling()
			.authenticationEntryPoint(authenticationEntryPoint)
			.accessDeniedHandler(accessDeniedHandler)
			.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.addFilter(customAuthenticationFilter)
				.addFilterBefore(customAuthorizationFilter, CustomAuthenticationFilter.class)
				.authorizeRequests().antMatchers(HttpMethod.POST,"/api/auth/**").permitAll()
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				.anyRequest()
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

	@Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of(ApplicationConstants.CLIENT_SIDE_URL));
        config.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Origin","Origin","ngsw-bypass", "Content-Type", "Accept","Authorization", "Access-Control-Allow-Headers"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
