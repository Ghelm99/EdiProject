package com.edi.securedbackend.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/* Simple refreshTokenResponse object that provides a new JWT access token. */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenResponse {

	@JsonProperty("access_token")
	private String accessToken;

}
