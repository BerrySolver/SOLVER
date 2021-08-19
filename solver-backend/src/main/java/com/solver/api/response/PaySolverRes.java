package com.solver.api.response;

import java.util.List;

import com.solver.common.model.BaseResponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("PaySolverRes")
public class PaySolverRes extends BaseResponse{	
	@ApiModelProperty(name="사용자 닉네임")
	private String nickname;
	
	@ApiModelProperty(name="사용자 프로필 url")
	private String profileUrl;
	
	@ApiModelProperty(name="사용자 포인트")
	private int point;
	
	public static PaySolverRes of(int i, String string, PaySolverRes userProfileRes) {
		PaySolverRes res = userProfileRes;
		res.setStatusCode(i);
		res.setMessage(string);
		
		return res;
	}
}
