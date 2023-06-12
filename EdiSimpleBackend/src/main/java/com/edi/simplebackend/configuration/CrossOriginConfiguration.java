package com.edi.simplebackend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrossOriginConfiguration {

	@Bean
	public WebMvcConfigurer crossOriginConfigurer() {
		return new WebMvcConfigurer() {

			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowCredentials(true)
						.allowedMethods("*")
						.allowedHeaders("*")
						.maxAge(3600);
			}
		};
	}

}
