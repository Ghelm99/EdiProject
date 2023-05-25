package com.edi.simplebackend.users.controller;

import com.edi.simplebackend.users.exceptions.UserNotFoundException;
import com.edi.simplebackend.users.exceptions.UsernameAlreadyTakenException;
import com.edi.simplebackend.users.exceptions.WrongPasswordException;
import com.edi.simplebackend.users.repository.UserRepository;
import com.edi.simplebackend.users.model.User;
import com.edi.simplebackend.users.model.UserData;
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
		return getUserByUsernameAndPassword(user.getUsername(), user.getPassword());
	}

	User getUserByUsernameAndPassword(final String username, final String password) throws UserNotFoundException,
			WrongPasswordException {

		return this.checkPassword(username, password);
	}

	private User checkPassword(final String username, final String password) throws UserNotFoundException,
			WrongPasswordException {

		Optional<UserData> userDataOptional = this.userRepository.findByUsername(username);

		if (userDataOptional.isEmpty()) {
			throw new UserNotFoundException("The user corresponding to the username: " + username + " does not " +
					"exist!");
		}

		User user = userDataOptional.get()
				.transferToUser();

		if (! (user.getPassword()
				.equals(password))) {
			throw new WrongPasswordException("The password you entered for user " + username + " is not valid!");
		}

		return user;
	}

	User getUserById(final Long userId) throws UserNotFoundException {

		final Optional<UserData> retrievedUser = this.userRepository.findById(userId);

		if (retrievedUser.isEmpty()) {
			throw new UserNotFoundException("User not found for id: " + userId);
		}

		return retrievedUser.get()
				.transferToUser();
	}

	User getUserByUsername(final String username) throws UserNotFoundException {

		Optional<UserData> userDataOptional = this.userRepository.findByUsername(username);

		if (userDataOptional.isPresent()) {
			throw new UsernameAlreadyTakenException("The username: " + username + " is already taken!");
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
			existingUserData.setUsername(updatedUserData.getUsername());
			existingUserData.setName(updatedUserData.getName());
			existingUserData.setSurname(updatedUserData.getSurname());
			existingUserData.setPassword(updatedUserData.getPassword());
			existingUserData.setEmail(updatedUserData.getEmail());
			existingUserData.setTelephoneNumber(updatedUserData.getTelephoneNumber());
			this.userRepository.save(existingUserData);
			outputUser = existingUserData.transferToUser();
		}

		return outputUser;
	}

}
