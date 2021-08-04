package com.solver.api.service;

import com.solver.api.request.ProfilePossibleTimePatchReq;
import com.solver.api.request.ProfileUpdatePatchReq;
import com.solver.api.response.ProfileRes;
import com.solver.api.response.ProfileTabRes;

public interface ProfileService {
	ProfileRes getProfileInfo(String nickname);

	void updateProfile(ProfileUpdatePatchReq profileUpdatePatchReq, String accessToken);

	void updateProfilePossibleTime(ProfilePossibleTimePatchReq profilePossibleTimePatchReq, String accessToken);

	ProfileTabRes getProfileTabInfo(String nickname, int tabNum);

	int followUser(String accessToken, String nickname);
}
