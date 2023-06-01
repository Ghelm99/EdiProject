package com.edi.simplebackend.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
	public ResponseEntity<?> login(@RequestBody Map<String, String> requestBody, HttpServletRequest request, HttpServletResponse response) {
		String email = requestBody.get("email");
		String password = requestBody.get("password");

		userSessionData.login(email, password);

		Map<String, String> userCredentials = new HashMap<>();
		userCredentials.put("email", userSessionData.getEmail());
		userCredentials.put("password", userSessionData.getPassword());
		userCredentials.put("cookieToken", userSessionData.getCookieToken());

		userSessionData.storeCookieTokenInResponse(userSessionData.getCookieToken() ,response);

		return ResponseEntity.ok(userCredentials);
	}


	@PostMapping(value = "/logout")
	public ResponseEntity<?> logout(HttpServletResponse response) {

		userSessionData.logout(response);
		return ResponseEntity.ok(userSessionData);
	}

}
