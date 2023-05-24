package com.edi.simplebackend.users.model;

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
	private String username;
	private String password;
	private String email;
	private String telephoneNumber;

	public UserData transferToUserData() {

		final UserData userData = new UserData();

		userData.setUserId(this.userId);
		userData.setName(this.name);
		userData.setSurname(this.surname);
		userData.setUsername(this.username);
		userData.setPassword(this.password);
		userData.setEmail(this.email);
		userData.setTelephoneNumber(this.telephoneNumber);

		return userData;
	}

}