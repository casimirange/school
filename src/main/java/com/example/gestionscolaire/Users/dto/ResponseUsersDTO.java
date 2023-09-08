package com.example.gestionscolaire.Users.dto;


import com.example.gestionscolaire.Users.entity.ERole;
import com.example.gestionscolaire.Users.entity.RoleUser;
import com.example.gestionscolaire.Users.entity.StatusUser;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class ResponseUsersDTO {

	private Long userId;

	private String email;

	private String telephone;

	private Set<RoleUser> roles;

	private StatusUser status;

	private List<ERole> roleNames;

	private String firstName;

	private String lastName;

	private LocalDateTime dateLastLogin;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

}
