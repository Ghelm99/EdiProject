package com.edi.simplebackend.login;

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
public class UserSessionData {

	private final UserRepository userRepository;
	private Long userId;
	private String userEmail;
	private String password;
	private Boolean isUserLoggedIn;

	public void login(String userEmail, String password) {

		final Optional<UserData> optionalUserData = userRepository.findByEmail(userEmail);

		if (optionalUserData.isPresent()) {
			User user = optionalUserData.get()
					.transferToUser();

			this.userId = user.getUserId();
			this.userEmail = user.getEmail();
			this.password = user.getPassword();
			isUserLoggedIn = true;

		}
	}

	public void logout() {

		userId = null;
		userEmail = null;
		password = null;
		isUserLoggedIn = false;
	}

}
