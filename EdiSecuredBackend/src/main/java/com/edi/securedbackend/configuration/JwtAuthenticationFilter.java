package com.edi.securedbackend.configuration;

import com.edi.securedbackend.tokens.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;
	private final TokenRepository tokenRepository;

	@Override
	protected void doFilterInternal(

			@NonNull HttpServletRequest request,
			@NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain

	) throws ServletException, IOException {

		final String authorizationHeader = request.getHeader("Authorization");
		final String jwtToken;
		final String email;

		/* Checking if the authorization header is missing or is not related to a Bearer token. */
		if (authorizationHeader == null || ! authorizationHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		/* Extracting JWT token and email from the authorization header. */
		jwtToken = authorizationHeader.substring(7);
		email = jwtService.extractEmail(jwtToken);

		/* Checking if the user is not yet authenticated. */
		if (email != null && SecurityContextHolder.getContext()
				.getAuthentication() == null) {

			/* Loading userDetails by email. */
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

			/* Checking if the JWT token is not expired or revoked. */
			Boolean isTokenValid = tokenRepository.findByToken(jwtToken)
					.map(token -> ! token.isExpired() && ! token.isRevoked())
					.orElse(false);

			/* If every JWT token is not expired or revoked.. */
			if (jwtService.isTokenValid(jwtToken, userDetails) && isTokenValid) {

				/* Creating an authentication token. */
				UsernamePasswordAuthenticationToken authenticationToken =
						new UsernamePasswordAuthenticationToken(
								userDetails,
								null,
								userDetails.getAuthorities()
						);

				/* Setting the authenticationToken's details. */
				authenticationToken.setDetails(
						new WebAuthenticationDetailsSource().buildDetails(request)
				);

				/* Placing the authenticationToken in the SecurityContextHolder. */
				SecurityContextHolder.getContext()
						.setAuthentication(authenticationToken);
			}
		}

		/* Continue the filter chain. */
		filterChain.doFilter(request, response);
	}

}
