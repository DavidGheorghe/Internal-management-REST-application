package com.dvd.jwt;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.dvd.entity.ApplicationUser;

import lombok.Data;

/**
* Utility class for JWT.
*
* @author David Gheorghe
*/
@Data
@Component
public class JwtUtils {
	@Value("${application.jwt.secret-key}")
	private String secretKey;
	@Value("${application.jwt.token-prefix}")
	private String tokenPrefix;
	@Value("${application.jwt.access-token-time-expiration-hours}")
	private Integer accessTokenTimeExpirationHours;
	@Value("${application.jwt.refresh-token-time-expiration-days}")
	private Integer refreshTokenTimeExpirationDays;
		
	public String generateAccessToken(Authentication authentication, String requestURL) {
		User user = (User) authentication.getPrincipal();
		String username = user.getUsername();
		List<String> authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		Algorithm algorithm = this.getAlgorithm();
		long expirationTime = this.getAccessTokenExpirationTime();
		Date expirationDate = new Date(expirationTime);
		
		String accessToken = JWT.create()
				.withSubject(username)
				.withExpiresAt(expirationDate)
				.withIssuer(requestURL)
				.withClaim("roles", authorities)
				.sign(algorithm);
		
		return accessToken;
	}
	
	public String generateRefreshToken(Authentication authentication, String requestURL) {
		User user = (User) authentication.getPrincipal();
		String username = user.getUsername();
		Algorithm algorithm = this.getAlgorithm();
		
		long expirationTime = this.getRefreshTokenExpirationTime();
		Date expirationDate = new Date(expirationTime);
		
		String refreshToken = JWT.create()
				.withSubject(username)
				.withExpiresAt(expirationDate)
				.withIssuer(requestURL)
				.sign(algorithm);
		
		return refreshToken;
	}
	
	public String generateRefreshTokenFromUsername(String username, String requestURL) {
//		User user = (User) authentication.getPrincipal();
//		String username = user.getUsername();
		Algorithm algorithm = this.getAlgorithm();
		
		long expirationTime = this.getRefreshTokenExpirationTime();
		Date expirationDate = new Date(expirationTime);
		
		String refreshToken = JWT.create()
				.withSubject(username)
				.withExpiresAt(expirationDate)
				.withIssuer(requestURL)
				.sign(algorithm);
		
		return refreshToken;
	}
	
	public String generateAccessTokenAfterExpiration(ApplicationUser user, String requestURL) {
		List<String> authorities = user.getAuthorities().stream().map(authority -> authority.getAuthority()).collect(Collectors.toList());
		Date expirationDate = new Date(this.getAccessTokenExpirationTime());
		String newAccessToken = JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(expirationDate)
				.withIssuer(requestURL)
				.withClaim("roles", authorities)
				.sign(this.getAlgorithm());
		return newAccessToken;
	}
	
	public DecodedJWT getDecodedToken(String token) {		
		Algorithm algorithm = this.getAlgorithm();
		JWTVerifier verifier = JWT.require(algorithm).build();
		DecodedJWT decodedJWT = verifier.verify(token);
		return decodedJWT;
	}
	
	public String getUsernameFromToken(String token) {
		return JWT.decode(token).getSubject();
	}
	
	public long getAccessTokenExpirationTime() {
		return System.currentTimeMillis() + this.getAccessTokenTimeExpirationHours() * 60 * 60 * 1000; // System.currentTimeMillis() + 60 * 1000
	}
	
	public long getRefreshTokenExpirationTime() {
		return System.currentTimeMillis() + this.getRefreshTokenTimeExpirationDays() * 24 * 60 * 60 * 1000;
	}
	
	public Algorithm getAlgorithm() {
		return Algorithm.HMAC256(this.getSecretKey().getBytes());
	}
	
	public boolean validateJwtToken(String token) {
//		try {
//			JWTVerifier verifier = JWT.require(getAlgorithm())
//					.withIssuer(token).build();
//		}
		return true;
	}
}
