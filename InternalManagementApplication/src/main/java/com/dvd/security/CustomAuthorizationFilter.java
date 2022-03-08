package com.dvd.security;

import static java.util.Arrays.stream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dvd.jwt.JwtUtils;
import com.dvd.utils.ApplicationConstants;

import lombok.RequiredArgsConstructor;

/**
* Defines the custom authorization filter for JWT Authorization.
*
* @author David Gheorghe
*/
@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {

	private final JwtUtils jwtConfig;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (request.getServletPath().equals(ApplicationConstants.AUTH_LOGIN) || request.getServletPath().equals(ApplicationConstants.AUTH_REFRESH_TOKEN)) {
			filterChain.doFilter(request, response);
		} else {
			String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
			if (authorizationHeader != null && authorizationHeader.startsWith(ApplicationConstants.JWT_AUTHORIZATION_PREFIX)) {
				try {
					String token = authorizationHeader.substring(ApplicationConstants.JWT_AUTHORIZATION_PREFIX.length());
					DecodedJWT decodedJWT = jwtConfig.getDecodedToken(token);

					String username = decodedJWT.getSubject();
					String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

					Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
					stream(roles).forEach(role -> {
						authorities.add(new SimpleGrantedAuthority(role));
					});

					UsernamePasswordAuthenticationToken authenticationToken = 
							new UsernamePasswordAuthenticationToken(username, null, authorities);

					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					filterChain.doFilter(request, response);
				} catch (JWTVerificationException e) {
					response.setHeader("Error", e.getMessage());
					response.setStatus(HttpStatus.FORBIDDEN.value());
					throw new JWTVerificationException(e.getMessage());
				}
			} else {
				filterChain.doFilter(request, response);
			}
		}
	}
}
