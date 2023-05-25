package com.edi.simplebackend.login;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userAccess")
public class LoginController {

	private final UserSessionData userSessionData;

	@PostMapping(value = "/login", params = {"userId", "username", "password"})
	public ResponseEntity<?> login(

			@RequestParam final String userId,
			@RequestParam final String username,
			@RequestParam final String password

	) {
		userSessionData.login(userId, username, password);
		return ResponseEntity.ok("The user: " + username + " has logged in!");
	}

	@PostMapping(value = "/logout")
	public ResponseEntity<String> logout() {

		userSessionData.logout();
		return ResponseEntity.ok("The user has logged out!");
	}

	@GetMapping
	public ResponseEntity<UserSessionData> getUserSessionData() {

		return ResponseEntity.ok().body(userSessionData);
	}

}
