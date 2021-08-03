package com.solver.api.service;

import com.solver.api.request.ProfileUpdatePatchReq;
import com.solver.api.response.ProfileRes;

public interface ProfileService {
	ProfileRes getProfileInfo(String nickname);

	void updateProfile(ProfileUpdatePatchReq profileUpdatePatchReq, String accessToken);
}
