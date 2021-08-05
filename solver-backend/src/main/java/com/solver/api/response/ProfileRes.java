package com.solver.api.response;

import java.util.List;

import com.solver.common.model.BaseResponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ProfileRes")
public class ProfileRes extends BaseResponse{
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
	
	@ApiModelProperty(name="화상 회의 시간")
	private String weekendTime;
	
	@ApiModelProperty(name="화상 회의 시간")
	private String weekdayTime;
	
	public static ProfileRes of(int i, String string, ProfileRes userProfileRes) {
		ProfileRes res = userProfileRes;
		res.setStatusCode(i);
		res.setMessage(string);
		
		return res;
	}
}
