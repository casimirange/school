package com.example.gestionscolaire.Users.repository;



import com.example.gestionscolaire.Users.entity.ERole;
import com.example.gestionscolaire.Users.entity.RoleUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleUserRepo extends JpaRepository<RoleUser, Long> {
	Optional<RoleUser> findByName(ERole name);
	boolean existsByName(ERole name);
}
