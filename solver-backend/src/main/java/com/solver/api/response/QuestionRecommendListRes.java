package com.solver.api.response;

import java.util.List;

import com.solver.common.model.BaseResponse;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("QuestionRecommendListResponse")
public class QuestionRecommendListRes extends BaseResponse {
	private List<QuestionRecommendRes> list;

	public static QuestionRecommendListRes of(Integer statusCode, String message, List<QuestionRecommendRes> questionResList) {
		QuestionRecommendListRes res = new QuestionRecommendListRes();
		res.setList(questionResList);
		res.setStatusCode(statusCode);
		res.setMessage(message);		

		return res;
	}
}
