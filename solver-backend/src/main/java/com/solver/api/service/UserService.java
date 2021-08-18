package com.solver.api.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import com.solver.api.request.SolverGetListReq;
import com.solver.api.request.UserRegistPostReq;
import com.solver.api.response.PaySolverRes;
import com.solver.api.response.SolverRes;
import com.solver.common.model.OAuthToken;
import com.solver.db.entity.user.Token;
import com.solver.db.entity.user.User;

public interface UserService {
	Optional<User> checkNickname(String nickname);

	Optional<User> getUserByKakaoId(Long kakaoId);

	User insertUser(Long kakaoId);

	Token insertToken(OAuthToken oauthToken, Long kakaoId);

	void deleteToken(String accessToken, HttpServletResponse response);

	void singUp(UserRegistPostReq userRegistPostReq, String accessToken, HttpServletResponse response);

	void deleteUser(String accessToken, HttpServletResponse response);

	List<SolverRes> getUserList(SolverGetListReq solverGetListReq);

	String getNickname(String accessToken, HttpServletResponse response);
	
	List<PaySolverRes> getPaySolver(String accessToken);

	void insertDefaultProfile(Long kakaoId, String profileUrl);
}
