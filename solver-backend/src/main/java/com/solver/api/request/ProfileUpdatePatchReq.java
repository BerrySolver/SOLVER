package com.solver.api.request;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserUpdatePatchReq")
public class ProfileUpdatePatchReq {
	@ApiModelProperty(name="nickname", example="dangna")
	private String nickname;
	@ApiModelProperty(name="introduction", example="updateIntroduction")
	private String introduction;
	@ApiModelProperty(name="linkText", example="www.ssafy.com")
	private String linkText;
	@ApiModelProperty(name="profileUrl", example="updateUrl")
	private String profileUrl;
	@ApiModelProperty(name="categoryList", example="")
	private List<String> categoryList;
}
