package com.dvd.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dvd.entity.ApplicationUser;
import com.dvd.repository.ApplicationUserRepository;

import lombok.RequiredArgsConstructor;

/**
* Defines the custom user details service used for security.
*
* @author David Gheorghe
*/
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private final ApplicationUserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {		
		ApplicationUser user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found!", username)));
		return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
	}

}
