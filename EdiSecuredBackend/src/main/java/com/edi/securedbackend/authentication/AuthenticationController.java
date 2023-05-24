package com.edi.securedbackend.authentication;

import com.edi.securedbackend.configuration.ApplicationConfiguration;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/* Access tokens and refresh tokens are commonly used in authentication and authorization systems to grant access to
protected resources. The access token is a credential that is used to access a protected resource, while the refresh
token is a credential that can be used to obtain a new access token after the original access token has expired.
The following code implements access and refresh tokens service encoding tokens following the JSON Web Token (JWT)
format. */

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthenticationService authenticationService;
	private final ApplicationConfiguration applicationConfiguration;

	/* Method used to authenticate already registered users.
	It returns a JWT (Authentication response object) or a bad request. */
	@PostMapping("/login")
	public ResponseEntity<Object> authenticate(

			@RequestBody AuthenticationRequest request

	) {
		AuthenticationResponse authenticationResponse = null;

		try {
			authenticationResponse = authenticationService.authenticate(request);
		} catch (Exception exception) {
			return ResponseEntity.badRequest()
					.body(exception.getMessage());
		}

		return ResponseEntity.ok(authenticationResponse);
	}

	/* Method that refreshes the JWT token. */
	@PostMapping("/refresh")
	public ResponseEntity<RefreshTokenResponse> refreshToken(

			HttpServletRequest request,
			HttpServletResponse response

	) {
		RefreshTokenResponse refreshTokenResponseResponse;

		try {
			refreshTokenResponseResponse = authenticationService.refreshToken(request, response);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return ResponseEntity.ok(refreshTokenResponseResponse);
	}

	/* Method used to register new users. It returns a JWT (AuthenticationResponse object). */
	@PostMapping("/signup")
	public ResponseEntity<AuthenticationResponse> register(

			@RequestBody RegisterRequest request

	) {
		return ResponseEntity.ok(authenticationService.register(request));
	}

	@GetMapping("/myprofile")
	public String showMyProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		return userDetails.getUsername();
	}

}
