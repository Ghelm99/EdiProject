package com.edi.securedbackend.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/* Simple authentication request object that contains all the user (userData) fields. */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

	private String name;
	private String surname;
	private String email;
	private String password;

}
