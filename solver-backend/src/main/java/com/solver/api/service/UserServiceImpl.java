package com.solver.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solver.api.request.UserRegistPostReq;
import com.solver.db.entity.Auth;
import com.solver.db.entity.Code;
import com.solver.db.entity.User;
import com.solver.db.repository.AuthRepository;
import com.solver.db.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AuthRepository authRepository;

	
	@Override
	public void createUser(UserRegistPostReq userRegistPostReq) {
		
//		User user = User.builder()
//				.loginId(userRegistPostReq.getLoginId())
//				.nickname(userRegistPostReq.getNickname())
//				.possibleTime(userRegistPostReq.getPossibleTime())
//				.build();
		
		User user = new User();
		user.setId("textuserId");
		
		Code code = new Code();
		code.setCode(userRegistPostReq.getType());
		
		user.setNickname(userRegistPostReq.getNickname());
		user.setCode(code);
		
		Auth auth = new Auth();
		auth.setId("textauthId");
		auth.setLoginId(userRegistPostReq.getLoginId());
		auth.setPassword(userRegistPostReq.getPassword());
//		
//		
//		userRepository.save(user);
		
	}
	
}
