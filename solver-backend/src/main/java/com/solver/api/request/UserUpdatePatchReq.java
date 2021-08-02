package com.solver.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserUpdatePatchReq")
public class UserUpdatePatchReq {
	@ApiModelProperty(name="nickname", example="updateNickname")
	private String nickname;
	@ApiModelProperty(name="introduction", example="updateIntroduction")
	private String introduction;
	@ApiModelProperty(name="linkText", example="www.ssafy.com")
	private String linkText;
	@ApiModelProperty(name="profileUrl", example="updateUrl")
	private String profileUrl;
}
