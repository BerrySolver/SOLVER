package com.solver.api.response;

import java.util.List;

import com.solver.common.model.BaseResponse;
import com.solver.db.entity.user.User;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserProfileRes")
public class UserProfileRes extends BaseResponse{
	@ApiModelProperty(name="전체 포인트")
	private int point; 
	
	@ApiModelProperty(name="남은 포인트")
	private int remainingPoint; 
	
	@ApiModelProperty(name="평점")
	private float evaluationScore;
	
	@ApiModelProperty(name="참여 그룹 목록")
	private List<String> groupNameList;
	
	@ApiModelProperty(name="관심 분야 목록")
	private List<String> favoriteFieldNameList;
	
	@ApiModelProperty(name="사용자 닉네임")
	private String nickname;
	
	@ApiModelProperty(name="사용자 소개")
	private String introduction;
	
	@ApiModelProperty(name="사용자 링크 정보")
	private String linkText;
	
	@ApiModelProperty(name="사용자 프로필 url")
	private String profileUrl;
	
	public static UserProfileRes of(int i, String string, UserProfileRes userProfileRes) {
		UserProfileRes res = userProfileRes;
		res.setStatusCode(i);
		res.setMessage(string);
		
		return res;
	}
}
