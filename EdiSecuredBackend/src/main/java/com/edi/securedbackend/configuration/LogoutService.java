package com.edi.securedbackend.configuration;

import com.edi.securedbackend.tokens.Token;
import com.edi.securedbackend.tokens.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

	private final TokenRepository tokenRepository;

	/* Method called when a user logs out. */
	@Override
	public void logout(

			HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication

	) {
		/* Getting the authenticationHeader from the request. */
		final String authenticationHeader = request.getHeader("Authorization");
		final String jwtToken;

		if (authenticationHeader == null || ! authenticationHeader.startsWith("Bearer ")) {
			return;
		}

		/* Extracting the JWT from the authentication header. */
		jwtToken = authenticationHeader.substring(7);

		/* Looking for the token in the tokenRepository. */
		Token storedToken = tokenRepository.findByToken(jwtToken)
				.orElse(null);

		if (storedToken != null) {

			/* Setting the token to expired and revoked. */
			storedToken.setExpired(true);
			storedToken.setRevoked(true);

			/* Saving the updated token in the tokenRepository. */
			tokenRepository.save(storedToken);

			/* Cleaning the security context holder. */
			SecurityContextHolder.clearContext();
		}
	}

}
