package com.solver.api.response;

import java.util.List;

import com.solver.common.model.BaseResponse;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("QuestionRecommendResponse")
public class QuestionRecommendRes extends BaseResponse {
	// Id
	private String questionId;
	// 제목
	private String title;
	// 내용
	private String content;
	// 답변 개수
	private int answerCount;
	// 좋아요 개수
	private int likeCount;
	// 북마크 개수
	private int bookMarkCount;
	// 조회수 개수
	private int readCount;

	public static QuestionRecommendRes of(Integer statusCode, String message, QuestionRecommendRes questionRes) {
		QuestionRecommendRes res = questionRes;
		res.setStatusCode(statusCode);
		res.setMessage(message);		

		return res;
	}
}
