package com.edi.simplebackend.users.controller;

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
	@Autowired
	private HttpServletRequest request;

	@PostMapping
	public ResponseEntity<User> addUser(@RequestBody final User user) {
		return ResponseEntity.ok(this.userService.addUser(user));
	}

	@PutMapping(params = {"email", "password", "newPassword"})
	public ResponseEntity<User> changeUserPassword(

			@RequestParam String email,
			@RequestParam String password,
			@RequestParam String newPassword

	) {

		if (!isUserLoggedIn()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		return ResponseEntity.ok(userService.changeUserPassword(email, password, newPassword));
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
	public ResponseEntity<User> updateUser(@RequestParam final Long id, @RequestBody final User user) {

		final User retreievedUser = this.userService.updateUser(id, user);

		if (!isUserLoggedIn()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		if (retreievedUser == null) {
			return ResponseEntity.notFound()
					.build();
		}

		return ResponseEntity.ok(retreievedUser);
	}

	private boolean isUserLoggedIn() {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("cookieToken".equals(cookie.getName())) {
					//currently just checking if the names are equal
					return true;
				}
			}
		}
		return false;
	}

}


