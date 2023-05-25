package com.edi.simplebackend.users.controller;

import com.edi.simplebackend.users.exceptions.UserNotFoundException;
import com.edi.simplebackend.users.exceptions.WrongPasswordException;
import com.edi.simplebackend.users.model.User;
import com.edi.simplebackend.users.model.UserData;
import com.edi.simplebackend.users.repository.UserRepository;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@RequiredArgsConstructor
@Component
class UserService {

	private final UserRepository userRepository;

	User addUser(final User user) {
		this.userRepository.save(user.transferToUserData());
		return getUserByEmailAndPassword(user.getEmail(), user.getPassword());
	}

	User getUserByEmailAndPassword(final String email, final String password) throws UserNotFoundException,
			WrongPasswordException {

		return this.checkPassword(email, password);
	}

	private User checkPassword(final String email, final String password) throws UserNotFoundException,
			WrongPasswordException {

		final Optional<UserData> userDataOptional = this.userRepository.findByEmail(email);

		if (userDataOptional.isEmpty()) {
			throw new UserNotFoundException("The user corresponding to the username: " + email + " does not " +
					"exist!");
		}

		final User user = userDataOptional.get()
				.transferToUser();

		if (! (user.getPassword()
				.equals(password))) {
			throw new WrongPasswordException("The password you entered for user " + email + " is not valid!");
		}

		return user;
	}

	User changeUserPassword(final String username, final String password, final String newPassword) {

		User outputUser = getUserByEmailAndPassword(username, password);

		if (outputUser != null) {
			outputUser.setPassword(newPassword);
		}

		return outputUser;
	}

	User getUserByEmail(final String email) throws UserNotFoundException {

		final Optional<UserData> userDataOptional = this.userRepository.findByEmail(email);

		if (userDataOptional.isEmpty()) {
			throw new UserNotFoundException("User not found for username: " + email);
		}

		return userDataOptional.get()
				.transferToUser();
	}

	User getUserById(final Long userId) throws UserNotFoundException {

		final Optional<UserData> userDataOptional = this.userRepository.findById(userId);

		if (userDataOptional.isEmpty()) {
			throw new UserNotFoundException("User not found for id: " + userId);
		}

		return userDataOptional.get()
				.transferToUser();
	}

	User updateUser(final Long userId, final User user) {

		final Optional<UserData> userDataToBeUpdated = this.userRepository.findById(userId);
		final UserData updatedUserData = user.transferToUserData();
		User outputUser = null;

		if (userDataToBeUpdated.isPresent()) {

			final UserData existingUserData = userDataToBeUpdated.get();
			existingUserData.setName(updatedUserData.getName());
			existingUserData.setSurname(updatedUserData.getSurname());
			existingUserData.setPassword(updatedUserData.getPassword());
			existingUserData.setEmail(updatedUserData.getEmail());
			this.userRepository.save(existingUserData);
			outputUser = existingUserData.transferToUser();
		}

		return outputUser;
	}

}
