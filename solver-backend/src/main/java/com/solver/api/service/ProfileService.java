package com.solver.api.service;

import javax.servlet.http.HttpServletResponse;

import com.solver.api.request.ProfilePossibleTimePatchReq;
import com.solver.api.request.ProfileUpdatePatchReq;
import com.solver.api.response.ProfileRes;
import com.solver.api.response.ProfileTabRes;

public interface ProfileService {
	ProfileRes getProfileInfo(String nickname);

	void updateProfile(ProfileUpdatePatchReq profileUpdatePatchReq, String accessToken, HttpServletResponse response);

	void updateProfilePossibleTime(ProfilePossibleTimePatchReq profilePossibleTimePatchReq, String accessToken, HttpServletResponse response);

	ProfileTabRes getProfileTabInfo(String nickname, int tabNum);

	int followUser(String accessToken, String nickname, HttpServletResponse response);

	int unFollowUser(String accessToken, String nickname, HttpServletResponse response);
}
