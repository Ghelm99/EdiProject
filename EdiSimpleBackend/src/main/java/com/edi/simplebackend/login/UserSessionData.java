package com.edi.simplebackend.login;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@RequiredArgsConstructor
@Component
public class UserSessionData {

	private String userId;

	private String username;

	private String password;

	private Boolean isUserLoggedIn;

	public void login(String userId, String username, String password) {

		this.userId = userId;
		this.username = username;
		this.password = password;
		isUserLoggedIn = true;
	}

	public void logout() {

		userId = null;
		username = null;
		password = null;
		isUserLoggedIn = false;
	}

}
