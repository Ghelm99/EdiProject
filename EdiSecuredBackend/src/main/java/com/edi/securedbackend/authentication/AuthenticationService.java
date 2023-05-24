package com.edi.securedbackend.authentication;

import com.edi.securedbackend.tokens.Token;
import com.edi.securedbackend.configuration.JwtService;
import com.edi.securedbackend.tokens.TokenRepository;
import com.edi.securedbackend.tokens.TokenType;
import com.edi.securedbackend.users.model.Role;
import com.edi.securedbackend.users.model.UserData;
import com.edi.securedbackend.users.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final TokenRepository tokenRepository;

	/* Method used to authenticate already registered users. */
	public AuthenticationResponse authenticate(AuthenticationRequest request) {

		/* Authenticating the user. */
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(),
						request.getPassword()
				)
		);

		/* Getting the userData from the database. */
		UserData userData = userRepository.findByEmail(request.getEmail())
				.orElseThrow();

		/* Generating new access and refresh tokens. */
		String accessToken = jwtService.generateToken(userData);
		String refreshToken = jwtService.generateRefreshToken(userData);

		/* Revoking all userData previous tokens. */
		revokeAllUserTokens(userData);

		/* Saving the new accessToken to the database. */
		saveUserToken(userData, accessToken);

		/* Returning the generated AuthenticationResponse. */
		return AuthenticationResponse
				.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();
	}

	/* Method used to revoke all userData "previous" tokens. */
	private void revokeAllUserTokens(UserData userData) {

		/* Looking for all valid tokens by userData id. */
		List<Token> validUserDataTokens =
				tokenRepository.findAllValidTokenByUser(userData.getUserId());

		if (validUserDataTokens.isEmpty()) {
			return;
		}

		/* For each valid token, setting it as expired and revoked. */
		validUserDataTokens.forEach(token -> {
			token.setExpired(true);
			token.setRevoked(true);
		});

		/* Updating all the involved tokens in the database. */
		tokenRepository.saveAll(validUserDataTokens);
	}

	/* Method that saves a userData token to the database. */
	private void saveUserToken(UserData userData, String token) {

		tokenRepository.save(
				Token.builder()
						.userData(userData)
						.token(token)
						.tokenType(TokenType.BEARER)
						.expired(false)
						.revoked(false)
						.build()
		);
	}

	/* Method used to refresh tokens. */
	public RefreshTokenResponse refreshToken(

			HttpServletRequest request,
			HttpServletResponse response

	) throws Exception {

		/* Getting the authorizationHeader from the request. */
		final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		final String refreshToken;
		final String userDataEmail;

		RefreshTokenResponse output = null;

		/* Checking if the header is null or is referring to a Bearer token. */
		if (authorizationHeader == null || ! authorizationHeader.startsWith("Bearer ")) {
			throw new Exception();
		}

		/* Getting the refreshToken and the userDataEmail from the authorizationHeader. */
		refreshToken = authorizationHeader.substring(7);
		userDataEmail = jwtService.extractEmail(refreshToken);

		if (userDataEmail != null) {

			/* Looking for the userData by email. */
			UserData userData = this.userRepository.findByEmail(userDataEmail)
					.orElseThrow();

			/* Checking if the refresh token is valid for the userData. */
			if (jwtService.isTokenValid(refreshToken, userData)) {

				/* Generating a new access token for the userData. */
				String accessToken = jwtService.generateToken(userData);

				/* Revoking all tokens. */
				revokeAllUserTokens(userData);

				/* Saving the new access token to the database. */
				saveUserToken(userData, accessToken);

				/* Creating a new authenticationResponse object. */
				output = RefreshTokenResponse.builder()
						.accessToken(accessToken)
						.build();
			}
		}

		return output;
	}

	/* Method that registers new users. */
	public AuthenticationResponse register(RegisterRequest request) {

		/* Creating a new user with the requested data. */
		UserData userData = UserData.builder()
				.name(request.getName())
				.surname(request.getSurname())
				.email(request.getEmail())
				.password(passwordEncoder.encode((request.getPassword())))
				.role(Role.USER)
				.build();

		/* Storing the new record in the userData table. */
		UserData savedUserData = userRepository.save(userData);

		/* Generating a JWT access and a JWT refresh token. */
		String accessToken = jwtService.generateToken(userData);
		String refreshToken = jwtService.generateRefreshToken(userData);

		/* Saving the tokens to the database. */
		saveUserToken(savedUserData, accessToken);

		/* Returning the generated AuthenticationResponse. */
		return AuthenticationResponse
				.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();
	}

}
