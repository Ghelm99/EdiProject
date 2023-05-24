package com.edi.simplebackend.users.controller;

import com.edi.simplebackend.users.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping(params = "userId")
	public ResponseEntity<User> getUserById(@RequestParam final Long userId) {
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}

	@GetMapping(params = {"username", "password"})
	public ResponseEntity<?> getUserByUsernameAndPassword(@RequestParam final String username,
														  @RequestParam final String password) {
		final User user;

		try {
			user = this.userService.getUserByUsernameAndPassword(username, password);
		} catch (final Exception exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());
		}

		return ResponseEntity.ok(user);
	}

	@GetMapping(params = "username")
	public ResponseEntity<?> getUserByUsername(@RequestParam final String username) {
		final User user;

		try {
			user = this.userService.getUserByUsername(username);
		} catch (final Exception exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());
		}

		return ResponseEntity.ok(user);
	}

	@PostMapping
	public ResponseEntity<User> addUser(@RequestBody final User user) {
		return ResponseEntity.ok(this.userService.addUser(user));
	}

	@PutMapping(params = "id")
	public ResponseEntity<User> updateUser(@RequestParam final Long id, @RequestBody final User user) {

		final User retreievedUser = this.userService.updateUser(id, user);

		if (retreievedUser == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(retreievedUser);
	}

}


