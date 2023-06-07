package com.edi.simplebackend.users.controller;

import com.edi.simplebackend.login.UserSessionData;
import com.edi.simplebackend.users.model.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	private final UserSessionData userSessionData;
	@Autowired
	private HttpServletRequest request;

	@PostMapping
	public ResponseEntity<User> addUser(@RequestBody final User user) {
		return ResponseEntity.ok(this.userService.addUser(user));
	}

	@PutMapping(params = {"email", "password", "newPassword"})
	public ResponseEntity<?> changeUserPassword(

			@RequestParam String email,
			@RequestParam String password,
			@RequestParam String newPassword,
			@RequestHeader(value = "Cookie", required = false) String cookie
	) {
		if (!isUserLoggedIn(cookie)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		try {
			User updatedUser = userService.changeUserPassword(email, password, newPassword);
			if (updatedUser != null) {
				return ResponseEntity.ok(updatedUser);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping(params = "email")
	public ResponseEntity<?> getUserByEmail(@RequestParam final String email) {
		final User user;

		try {
			user = this.userService.getUserByEmail(email);
		} catch (final Exception exception) {
			return ResponseEntity.badRequest()
					.body(exception.getMessage());
		}

		return ResponseEntity.ok(user);
	}

	@GetMapping(params = {"email", "password"})
	public ResponseEntity<?> getUserByEmailAndPassword(
			@RequestParam final String email,
			@RequestParam final String password
	) {
		final User user;

		try {
			user = this.userService.getUserByEmailAndPassword(email, password);
		} catch (final Exception exception) {
			return ResponseEntity.badRequest()
					.body(exception.getMessage());
		}

		return ResponseEntity.ok(user);
	}

	@GetMapping(params = "userId")
	public ResponseEntity<User> getUserById(@RequestParam final Long userId) {
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}

	@PutMapping(params = "id")
	public ResponseEntity<User> updateUser(
			@RequestParam final Long id,
			@RequestBody final User user,
			@RequestHeader(value = "Cookie", required = false) String cookie) {
		if (!isUserLoggedIn(cookie)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		final User retreievedUser = this.userService.updateUser(id, user);

		if (retreievedUser == null) {
			return ResponseEntity.notFound()
					.build();
		}

		return ResponseEntity.ok(retreievedUser);
	}

	private boolean isUserLoggedIn(String cookie) {
		if (cookie != null && cookie.contains("cookieToken")) {
			// Extract the cookie token from the "cookie" string
			String[] cookieParts = cookie.split(";");
			for (String cookiePart : cookieParts) {
				if (cookiePart.trim().startsWith("cookieToken=")) {
					String token = cookiePart.trim().substring("cookieToken=".length());
					return token.equals(userSessionData.getCookieToken());
				}
			}
		}
		return false;
	}

}


