package com.solver.api.response;

import java.util.List;

import com.solver.common.model.BaseResponse;
import com.solver.db.entity.question.Question;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ProfileTabRes")
public class ProfileTabRes extends BaseResponse{
	private List<Question> answerQuestionList;
	
	private List<Question> myQuestionList;
	
	private int textAnswerCount;
	
	private int videoAnswerCount;
	
	private float videoAnswerTime;
}
