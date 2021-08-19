package com.solver.api.response;

import java.util.ArrayList;
import java.util.List;

import com.solver.common.model.BaseResponse;
import com.solver.db.entity.user.FavoriteUser;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("FollowListRes")
public class FollowListRes extends BaseResponse{
	private List<FollowInfo> followList;
	
	public static FollowListRes of(Integer statusCode, String message, List<FavoriteUser> followList, int mode) {
		FollowListRes res = new FollowListRes();
		
		List<FollowInfo> tempFollowList = new ArrayList<FollowInfo>();
		
		if (mode == 0) {
			for (FavoriteUser user : followList) {
				FollowInfo followInfo = new FollowInfo();
				followInfo.setNickname(user.getUser().getNickname());
				followInfo.setProfileUrl(user.getUser().getProfileUrl());
				tempFollowList.add(followInfo);
			}
		} else {
			for (FavoriteUser user : followList) {
				FollowInfo followInfo = new FollowInfo();
				followInfo.setNickname(user.getFollowingUser().getNickname());
				followInfo.setProfileUrl(user.getFollowingUser().getProfileUrl());
				tempFollowList.add(followInfo);
			}
		}
		
		
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setFollowList(tempFollowList);
		
		return res;
	}

}

@Getter
@Setter
class FollowInfo {
	private String nickname;
	private String profileUrl;
}