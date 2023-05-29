package com.edi.simplebackend.login;

import com.edi.simplebackend.users.model.User;
import com.edi.simplebackend.users.model.UserData;
import com.edi.simplebackend.users.repository.UserRepository;
import java.util.Optional;
import java.util.UUID;

import jakarta.servlet.http.HttpSession;
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
	private String email;
	private String password;
	private Boolean isUserLoggedIn;
	private String cookieToken;

	public void login(String email, String password, HttpSession httpSession) {

		final Optional<UserData> optionalUserData = userRepository.findByEmail(email);

		if (optionalUserData.isPresent()) {
			User user = optionalUserData.get()
					.transferToUser();

			if (user.getPassword()
					.equals(password)) {

				this.userId = user.getUserId();
				this.email = user.getEmail();
				this.password = user.getPassword();
				this.isUserLoggedIn = true;
				this.cookieToken = generateCookieToken();
				storeCookieTokenInSession(this.cookieToken, httpSession);

			} else {
				this.userId = null;
				this.email = "";
				this.password = "";
				this.isUserLoggedIn = false;
				this.cookieToken = null;
			}
		}
	}

	public void logout() {

		userId = null;
		email = null;
		password = null;
		isUserLoggedIn = false;
		cookieToken = null;

	}

	private String generateCookieToken() {
		return UUID.randomUUID().toString().replace("-","");
	}

	private void storeCookieTokenInSession(String token, HttpSession httpSession) {
		httpSession.setAttribute("cookieToken", token);
	}
}