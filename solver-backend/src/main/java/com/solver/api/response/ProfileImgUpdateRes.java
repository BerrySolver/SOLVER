package com.solver.api.response;

import java.util.List;

import com.solver.common.model.BaseResponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ProfileImgUpdateRes")
public class ProfileImgUpdateRes extends BaseResponse{
	@ApiModelProperty(name = "프로필 이미지")
	private String profileImgUrl;
	
	public static ProfileImgUpdateRes of(Integer statusCode, String message, String profileImgUrl) {
		ProfileImgUpdateRes res = new ProfileImgUpdateRes();
		res.setProfileImgUrl(profileImgUrl);
		res.setStatusCode(statusCode);
		res.setMessage(message);		

		return res;
	}

}
