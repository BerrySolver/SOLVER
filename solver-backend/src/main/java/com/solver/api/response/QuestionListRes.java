package com.solver.api.response;

import java.util.List;

import com.solver.common.model.BaseResponse;
import com.solver.db.entity.question.Question;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("QuestionListResponse")
public class QuestionListRes extends BaseResponse{
	private List<Question> questionList;
	
	public static QuestionListRes of(Integer statusCode, String message, List<Question> questionList) {
		QuestionListRes res = new QuestionListRes();
		
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setQuestionList(questionList);
		
		return res;
	}
}
