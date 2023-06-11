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
				registry.addMapping("/loans*")
						.allowedOriginPatterns("https://64861e0d2c003b4038beaa01--splendid-bublanina-92bdae.netlify.app")
						.allowedMethods("*")
						.allowedHeaders("*")
						.allowCredentials(true)
						.maxAge(3600);
			}
		};
	}

}
