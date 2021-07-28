package com.solver.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.solver.api.request.UserRegistPostReq;
import com.solver.db.entity.Auth;
import com.solver.db.entity.Code;
import com.solver.db.entity.User;
import com.solver.db.entity.UserCalendar;
import com.solver.db.repository.AuthRepository;
import com.solver.db.repository.UserCalendarRepository;
import com.solver.db.repository.UserRepository;

import com.solver.common.util.RandomIdUtil;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AuthRepository authRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserCalendarRepository userCalendarRepository;

	
	@Override
	public void createUser(UserRegistPostReq userRegistPostReq) {
		
//		User user = User.builder()
//				.loginId(userRegistPostReq.getLoginId())
//				.nickname(userRegistPostReq.getNickname())
//				.possibleTime(userRegistPostReq.getPossibleTime())
//				.build();
		
		User user = new User();
		String userId = "";
		
		while(true) {
			userId = RandomIdUtil.makeRandomId(13);
			
			if(authRepository.findById(userId) != null)
				break;
		}
		
		user.setId(userId);
		
		
		Code code = new Code();
		code.setCode(userRegistPostReq.getType());
		
		user.setNickname(userRegistPostReq.getNickname());
		user.setCode(code);
		
		Auth auth = new Auth();
		auth.setLoginId(userRegistPostReq.getLoginId());
		auth.setPassword(passwordEncoder.encode(userRegistPostReq.getPassword()));
		
		String authId = "";
		
		while(true) {
			authId = RandomIdUtil.makeRandomId(13);
			
			if(authRepository.findById(authId) != null)
				break;
		}
		
		auth.setId(authId);
		
		UserCalendar userCalender = new UserCalendar();
		userCalender.setPossibleTime(userRegistPostReq.getPossibleTime());
		
		String userCalenderId = "";
		
		while(true) {
			userCalenderId = RandomIdUtil.makeRandomId(13);
			
			if(authRepository.findById(userCalenderId) != null)
				break;
		}
		
		userCalender.setId(userCalenderId);
		userCalender.setUser(user);
//		
//		
//		userRepository.save(user);
//		authRepository.save(auth);
//		userCalendarRepository.save(userCalender);
		
	}
	
}
