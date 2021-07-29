package com.solver.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.solver.api.request.UserLoginPostReq;
import com.solver.api.request.UserRegistPostReq;
import com.solver.common.util.JwtTokenUtil;
import com.solver.common.util.RandomIdUtil;
import com.solver.db.entity.Auth;
import com.solver.db.entity.Code;
import com.solver.db.entity.Token;
import com.solver.db.entity.User;
import com.solver.db.entity.UserCalendar;
import com.solver.db.repository.AuthRepository;
import com.solver.db.repository.TokenRepository;
import com.solver.db.repository.UserCalendarRepository;
import com.solver.db.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AuthRepository authRepository;
	
	@Autowired
	TokenRepository tokenRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserCalendarRepository userCalendarRepository;
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;

	
	@Override
	public void createUser(UserRegistPostReq userRegistPostReq) {
		
		User user = new User();
		String userId = "";
		
		while(true) {
			userId = RandomIdUtil.makeRandomId(13);
			
			if(authRepository.findById(userId).orElse(null) == null)
				break;
		}
		user.setId(userId);
		
		Code code = new Code();
		code.setCode(userRegistPostReq.getType());
		
		user.setNickname(userRegistPostReq.getNickname());
		user.setLoginId(userRegistPostReq.getLoginId());
		
		Auth auth = new Auth();
		auth.setLoginId(user.getLoginId());
		auth.setPassword(passwordEncoder.encode(userRegistPostReq.getPassword()));
		
		String authId = "";
		
		while(true) {
			authId = RandomIdUtil.makeRandomId(13);
			
			if(authRepository.findById(authId).orElse(null) == null)
				break;
		}
		
		auth.setId(authId);
		
		UserCalendar userCalender = new UserCalendar();
		userCalender.setPossibleTime(userRegistPostReq.getPossibleTime());
		
		String userCalenderId = "";
		
		while(true) {
			userCalenderId = RandomIdUtil.makeRandomId(13);
			
			if(authRepository.findById(userCalenderId).orElse(null) == null)
				break;
		}
		
		userCalender.setId(userCalenderId);
		userCalender.setUser(user);
		
		userRepository.save(user);
		authRepository.save(auth);
		userCalendarRepository.save(userCalender);
		
	}
	
	@Override
	public Optional<User> checkNickname(String nickname) {
		Optional<User> user = userRepository.findByNickname(nickname);
		
		return user;
	}

	@Override
	public Optional<User> checkLoginId(String loginID) {
		Optional<User> user = userRepository.findByLoginId(loginID);
		
		return user;
	}
	
	@Override
	public Optional<Auth> loginUser(UserLoginPostReq userLoginPostReq) {
		Optional<Auth> auth = authRepository.findByLoginIdAndPassword(userLoginPostReq.getLoginId(), userLoginPostReq.getPassword());
		
		return auth;
	}

	@Override
	public String makeToken(User user) {
		String accessToken = jwtTokenUtil.createAccessToken(user);
		String refreshToken = jwtTokenUtil.createRefreshToken(user);
		
		String tokenId = "";
		
		while(true) {
			tokenId = RandomIdUtil.makeRandomId(13);
			
			if(tokenRepository.findById(tokenId).orElse(null) == null)
				break;
		}
		
		Token token = new Token();
		
		token.setAccessToken(accessToken);
		token.setRefreshToken(refreshToken);
		token.setId(tokenId);
		 
		tokenRepository.save(token);
		
		return accessToken;
	}

	@Override
	public Optional<User> getUserInfoByNickname(String nickname) {
		Optional<User> user = userRepository.findByNickname(nickname);
		
		return user;
	}
	
}
