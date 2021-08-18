package com.solver.api.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import com.solver.api.request.ProfilePossibleTimePatchReq;
import com.solver.api.request.ProfileUpdatePatchReq;
import com.solver.api.response.ProfileRes;
import com.solver.api.response.ProfileTabRes;
import com.solver.db.entity.user.FavoriteUser;
import com.solver.db.entity.user.User;

public interface ProfileService {
	ProfileRes getProfileInfo(String token, String nickname);

	void updateProfile(ProfileUpdatePatchReq profileUpdatePatchReq, String accessToken, HttpServletResponse response);

	void updateProfilePossibleTime(ProfilePossibleTimePatchReq profilePossibleTimePatchReq, String accessToken, HttpServletResponse response);

	ProfileTabRes getProfileTabInfo(String nickname, int tabNum);

	int followUser(String accessToken, String nickname, HttpServletResponse response);

	int unFollowUser(String accessToken, String nickname, HttpServletResponse response);

	User getByNickname(String token, String nickname);

	List<FavoriteUser> getFollowList(User user, int mode);
}
