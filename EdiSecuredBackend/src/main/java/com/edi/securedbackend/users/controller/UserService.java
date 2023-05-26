package com.edi.securedbackend.users.controller;

import com.edi.securedbackend.users.exceptions.EmailAlreadyTakenException;
import com.edi.securedbackend.users.exceptions.UserNotFoundException;
import com.edi.securedbackend.users.exceptions.WrongPasswordException;
import com.edi.securedbackend.users.model.UserData;
import com.edi.securedbackend.users.repository.UserRepository;
import com.edi.securedbackend.users.model.User;
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

		Optional<UserData> userDataOptional = this.userRepository.findByEmail(email);

		if (userDataOptional.isEmpty()) {
			throw new UserNotFoundException("The user corresponding to the email: " + email + " does not " +
					"exist!");
		}

		User user = userDataOptional.get()
				.transferToUser();

		if (! (user.getPassword()
				.equals(password))) {
			throw new WrongPasswordException("The password you entered for user " + email + " is not valid!");
		}

		return user;
	}

	User getUserByEmail(final String email) throws UserNotFoundException {

		Optional<UserData> userDataOptional = this.userRepository.findByEmail(email);

		if (userDataOptional.isPresent()) {
			throw new EmailAlreadyTakenException("The email: " + email + " is already taken!");
		}

		return userDataOptional.get()
				.transferToUser();
	}

	User getUserById(final Long userId) throws UserNotFoundException {

		final Optional<UserData> retrievedUser = this.userRepository.findById(userId);

		if (retrievedUser.isEmpty()) {
			throw new UserNotFoundException("User not found for id: " + userId);
		}

		return retrievedUser.get()
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
			outputUser = existingUserData.transferToUser();
		}

		return outputUser;
	}

}
