package com.safexpress.propeli.bff.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.client.RestTemplate;

import com.safexpress.propeli.servicebase.annotation.SFXApi;

/**
 * @author Arun Singh
 *@ClassType Configuration	
 *@Description Beans configuration for service
 */
@Configuration
@SFXApi
public class AppConfiguration {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfig() {
		return new PropertySourcesPlaceholderConfigurer();
	}

}
