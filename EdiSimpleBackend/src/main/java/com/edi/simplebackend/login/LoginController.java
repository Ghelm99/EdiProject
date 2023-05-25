package com.edi.simplebackend.login;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userAccess")
public class LoginController {

	private final UserSessionData userSessionData;

	@GetMapping
	public ResponseEntity<UserSessionData> getUserSessionData() {

		return ResponseEntity.ok()
				.body(userSessionData);
	}

	@PostMapping(value = "/login", params = {"email", "password"})
	public ResponseEntity<?> login(

			@RequestParam final String email,
			@RequestParam final String password

	) {
		userSessionData.login(email, password);
		return ResponseEntity.ok(userSessionData.getEmail());
	}

	@PostMapping(value = "/logout")
	public ResponseEntity<?> logout() {

		userSessionData.logout();
		return ResponseEntity.ok(userSessionData);
	}

}
