package com.edi.simplebackend.login;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> requestBody, HttpServletRequest request) {
		String email = requestBody.get("email");
		String password = requestBody.get("password");

		userSessionData.login(email, password, request.getSession());

		Map<String, String> userCredentials = new HashMap<>();
		userCredentials.put("email", userSessionData.getEmail());
		userCredentials.put("password", userSessionData.getPassword());
		userCredentials.put("cookieToken", userSessionData.getCookieToken());

		return ResponseEntity.ok(userCredentials);
	}


	@PostMapping(value = "/logout")
	public ResponseEntity<?> logout() {

		userSessionData.logout();
		return ResponseEntity.ok(userSessionData);
	}

}
