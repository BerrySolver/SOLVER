package com.solver.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.solver.api.request.UserRegistPostReq;
import com.solver.api.request.UserUpdatePatchReq;
import com.solver.db.entity.user.User;
import com.solver.db.entity.user.UserCalendar;
import com.solver.db.repository.user.UserCalendarRepository;
import com.solver.db.repository.user.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	
	@Override
	public Optional<User> checkNickname(String nickname) {
		Optional<User> user = userRepository.findByNickname(nickname);
		
		return user;
	}
}
