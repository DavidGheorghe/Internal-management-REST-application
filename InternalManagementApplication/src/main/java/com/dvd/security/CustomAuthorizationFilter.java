package com.dvd.security;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.dvd.exception.LoginFailedException;
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
		String path = request.getServletPath();
		if (path.equals(ApplicationConstants.AUTH_LOGIN) || path.equals(ApplicationConstants.AUTH_REFRESH_TOKEN)) {
			filterChain.doFilter(request, response);
		} else {
			processCookie(request, response, filterChain);
		}
	}

	private void processCookie(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

		Cookie[] cookies = request.getCookies();
		Cookie jwtCookie = null;
		if (cookies != null) {
			if (cookies.length > 0) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("access-token")) {
						jwtCookie = cookie;
						break;
					}
				}
			}
		}
		
		if (jwtCookie != null && jwtCookie.getValue().isEmpty() == false) {
			try {
				String token = jwtCookie.getValue();
				DecodedJWT decodedJWT = jwtConfig.getDecodedToken(token);

				String username = decodedJWT.getSubject();
				String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

				Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
				for (String role : roles) {
					authorities.add(new SimpleGrantedAuthority(role));
				}

				UsernamePasswordAuthenticationToken authenticationToken = 
						new UsernamePasswordAuthenticationToken(username, null, authorities);

				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				filterChain.doFilter(request, response);
			} catch (Exception e) {
				throw new LoginFailedException(e.getMessage());
			} 
		} else {
			filterChain.doFilter(request, response);
		}
	}
}
