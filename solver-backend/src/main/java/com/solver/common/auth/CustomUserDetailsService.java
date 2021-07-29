package com.solver.common.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.solver.db.entity.Auth;
import com.solver.db.entity.User;
import com.solver.db.repository.AuthRepository;
import com.solver.db.repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AuthRepository authRepository;

	@Override
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
		
		User user = userRepository.findByLoginId(loginId).get();
		Auth auth =  authRepository.findByLoginId(loginId).get();
		
		System.out.println(user.getId());
		System.out.println(auth.getId());
		
		if(user != null)
			return new CustomUserDetails(user, auth);
		
		return null;
		
	}
	

}
