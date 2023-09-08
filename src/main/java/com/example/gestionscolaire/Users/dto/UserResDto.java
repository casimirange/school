package com.example.gestionscolaire.Users.dto;

import com.example.gestionscolaire.Users.entity.RoleUser;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonInclude(value = Include.NON_NULL)
public class UserResDto{
	
	private Long userId;

	private Long internalReference;

	private String email;

	private String telephone;

	private String lastName;

	private String firstName;

	private Double montant;

	private List<RoleUser> roles;




}
