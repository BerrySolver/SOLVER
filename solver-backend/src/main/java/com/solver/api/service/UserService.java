package com.solver.api.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.solver.api.request.UserLoginPostReq;
import com.solver.api.request.UserRegistPostReq;
import com.solver.db.entity.Auth;
import com.solver.db.entity.User;

public interface UserService {
	void createUser(UserRegistPostReq userRegistPostReq);

	Optional<User> checkNickname(String nickname);
	Optional<User> checkLoginId(String loginID);

	Optional<Auth> loginUser(UserLoginPostReq userLoginPostReq);

	String makeToken(User user);
}
