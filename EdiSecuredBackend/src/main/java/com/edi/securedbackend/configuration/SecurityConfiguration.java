package com.edi.securedbackend.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final JwtAuthenticationFilter jwtAuthFilter;
	private final AuthenticationProvider authenticationProvider;
	private final LogoutHandler logoutHandler;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.cors(Customizer.withDefaults())
				.csrf()
				.disable()
				//.requiresChannel(channel -> channel.anyRequest().requiresSecure())
				.authorizeRequests()
				.requestMatchers("/auth/**")
				.permitAll()
				.requestMatchers("/books*")
				.permitAll()
				//.requestMatchers("/loans")
				//.permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.sessionManagement()
				/* Use stateless sessions since we are using JWT tokens. */
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				/* Use the custom authentication provider. */
				.authenticationProvider(authenticationProvider)
				/* Add the JWT authentication filter before the username/password filter. */
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.logout()
				/* URL to logout users. */
				.logoutUrl("auth/logout")
				/* Clear the authentication when the user logs out. */
				.logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext()));

		return http.build();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
		corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
		corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);
		return source;
	}

}
