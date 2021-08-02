package com.solver.api.service;

import java.util.Optional;

import com.solver.api.request.UserRegistPostReq;
import com.solver.api.request.UserUpdatePatchReq;
import com.solver.db.entity.user.User;

public interface UserService {
	Optional<User> checkNickname(String nickname);
}
