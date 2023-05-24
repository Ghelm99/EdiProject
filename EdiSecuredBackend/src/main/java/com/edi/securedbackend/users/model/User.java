package com.edi.securedbackend.users.model;

/* Data Transfer Object */

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {

	private Long userId;
	private String name;
	private String surname;
	private String email;
	private String password;

	public UserData transferToUserData() {

		final UserData userData = new UserData();

		userData.setUserId(this.userId);
		userData.setName(this.name);
		userData.setSurname(this.surname);
		userData.setEmail(this.email);
		userData.setPassword(this.password);

		return userData;
	}

}