package com.example.gestionscolaire.authentication.dto;

import com.example.gestionscolaire.Users.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthSignInResDto {

	private String bearerToken;
	private String refreshToken;
	private String tokenType;
	private Users user;
	private List<String> roles;
	private boolean authenticated;
	public AuthSignInResDto(String bearerToken) {
		super();
		this.bearerToken = bearerToken;
	}

}
