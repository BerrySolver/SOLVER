package com.solver.api.response;

import java.util.List;

import com.solver.common.model.BaseResponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("SolverResponse")
public class SolverRes extends BaseResponse {

	@ApiModelProperty(name = "사용자 프로필 url")
	private String profileUrl;

	@ApiModelProperty(name = "유저 닉네임")
	private String nickname;

	@ApiModelProperty(name = "전체 포인트")
	private int point;
	
	@ApiModelProperty(name = "평점")
	private float evaluationScore;

	@ApiModelProperty(name = "관심 분야 목록")
	private List<String> favoriteFieldNameList;

	public static SolverRes of(Integer statusCode, String message, SolverRes solverRes) {
		SolverRes res = solverRes;
		res.setStatusCode(statusCode);
		res.setMessage(message);		

		return res;
	}
}
