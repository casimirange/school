
package com.example.gestionscolaire.authentication.service;


import com.example.gestionscolaire.Users.entity.Users;
import com.example.gestionscolaire.Users.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private IUserRepo userRepo;

	@Override
//	@Transactional
	public UserDetails loadUserByUsername(String username)  throws UsernameNotFoundException{
		Users user = userRepo.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("l'utilisateur " + username + " n'a pas été trouvé"));
		return UserDetailsImpl.build(user);
	}

}
