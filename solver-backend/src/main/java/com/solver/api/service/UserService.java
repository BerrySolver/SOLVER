package com.solver.api.service;

import org.springframework.stereotype.Service;

import com.solver.api.request.UserRegistPostReq;

public interface UserService {
	void createUser(UserRegistPostReq userRegistPostReq);
}
