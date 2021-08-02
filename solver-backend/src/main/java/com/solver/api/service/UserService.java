package com.solver.api.service;

import java.util.Optional;

import com.solver.api.request.UserRegistPostReq;
import com.solver.api.request.UserUpdatePatchReq;
import com.solver.db.entity.user.User;

public interface UserService {
	void createUser(UserRegistPostReq userRegistPostReq);

	Optional<User> checkNickname(String nickname);
	Optional<User> checkLoginId(String loginID);

	Optional<User> getUserInfoByLoginId(String loginId);

	void deleteUser(String tokenNickname);
	
	void updateUser(UserUpdatePatchReq userUpdatePatchReq, String tokenNickname);
}
