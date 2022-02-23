package com.dvd.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
* Configuration class for beans used in application.
*
* @author David Gheorghe
*/
@Configuration
public class BeanConfig {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
}
