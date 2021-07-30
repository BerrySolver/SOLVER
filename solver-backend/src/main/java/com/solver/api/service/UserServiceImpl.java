package com.solver.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.solver.api.request.UserLoginPostReq;
import com.solver.api.request.UserRegistPostReq;
import com.solver.api.request.UserUpdatePatchReq;
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
	
	private static final String String = null;

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
		
		//유저 테이블 아이디 생성
		while(true) {
			userId = RandomIdUtil.makeRandomId(13);
			
			if(authRepository.findById(userId).orElse(null) == null)
				break;
		}
		user.setId(userId);
		
//		Code code = new Code();
//		code.setCode(userRegistPostReq.getType());
		
		user.setNickname(userRegistPostReq.getNickname());
		user.setLoginId(userRegistPostReq.getLoginId());
		
		Auth auth = new Auth();
		auth.setLoginId(user.getLoginId());
		auth.setPassword(passwordEncoder.encode(userRegistPostReq.getPassword()));
		
		String authId = "";
		
		//auth 테이블 id 생성
		while(true) {
			authId = RandomIdUtil.makeRandomId(13);
			
			if(authRepository.findById(authId).orElse(null) == null)
				break;
		}
		
		auth.setId(authId);
		
		UserCalendar userCalender = new UserCalendar();
		userCalender.setPossibleTime(userRegistPostReq.getPossibleTime());
		
		String userCalenderId = "";
		
		//userCalendar id생성
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
		Optional<Auth> auth = authRepository.findByLoginId(userLoginPostReq.getLoginId());
		
		// 없는 유저인 경우
		if (auth.orElse(null) == null) {
			auth = Optional.empty();
			return auth;
		}
		
		//입력받은 비밀번호가 DB에 저장된 비밀번호와 일치하는 경우
		if(!passwordEncoder.matches(userLoginPostReq.getPassword(), auth.get().getPassword())) {
			auth = Optional.empty();
		}
		
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
	public Optional<User> getUserInfoByLoginId(String loginId) {
		Optional<User> user = userRepository.findByLoginId(loginId);
		
		return user;
	}

	@Override
	public void deleteUser(String loginId) {
		Optional<User> user = userRepository.findByLoginId(loginId);
		
		//로그인 정보 제거
		authRepository.deleteByLoginId(user.get().getLoginId());
		//유저 화상 시간표 정보 제거
		userCalendarRepository.deleteByUserId(user.get().getId());
		//유저 정보 제거
		userRepository.deleteById(user.get().getId());
		
		return;
	}


	@Override
	public void updateUser(UserUpdatePatchReq userUpdatePatchReq, String loginId) {
		//해당 로그인 아이디로 유저 정보 가져옴
		Optional<User> optionalUser = userRepository.findByLoginId(loginId);
		
		//없는 유저인 경우 종료
		if(optionalUser.orElse(null) == null)
			return;
		
		User user = optionalUser.get();
		
		//유저 변경할 유저 정보 저장
		user.setIntroduction(userUpdatePatchReq.getIntroduction());
		user.setLinkText(userUpdatePatchReq.getLinkText());
		user.setNickname(userUpdatePatchReq.getNickname());
		user.setProfileUrl(userUpdatePatchReq.getProfileUrl());
		
		//유저 정보 업데이트
		userRepository.save(user);
	}
	
}
