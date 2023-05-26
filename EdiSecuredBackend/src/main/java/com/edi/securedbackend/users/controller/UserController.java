package com.edi.securedbackend.users.controller;

import com.edi.securedbackend.users.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	@PostMapping
	public ResponseEntity<User> addUser(@RequestBody final User user) {
		return ResponseEntity.ok(this.userService.addUser(user));
	}

	@GetMapping(params = "userId")
	public ResponseEntity<User> getUserById(@RequestParam final Long userId) {
		return ResponseEntity.ok(this.userService.getUserById(userId));
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

	@PutMapping(params = "id")
	public ResponseEntity<User> updateUser(@RequestParam final Long id, @RequestBody final User user) {

		final User retreievedUser = this.userService.updateUser(id, user);

		if (retreievedUser == null) {
			return ResponseEntity.notFound()
					.build();
		}

		return ResponseEntity.ok(retreievedUser);
	}

}


