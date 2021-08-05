package com.solver.api.response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.solver.common.model.BaseResponse;
import com.solver.db.entity.answer.Answer;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("AnswerListRes")
public class AnswerListRes extends BaseResponse{
	private List<AnswerInfo> answerList;
	
	public static AnswerListRes of(Integer statusCode, String message, List<Answer> answerList) {
		AnswerListRes res = new AnswerListRes();
		
		List<AnswerInfo> tempAnswerList = new ArrayList<AnswerInfo>();
		
		for (Answer answer : answerList) {
			AnswerInfo answerInfo = new AnswerInfo();
			
			answerInfo.setContent(answer.getContent());
			answerInfo.setId(answer.getId());
			answerInfo.setRegDt(answer.getRegDt());
			
			tempAnswerList.add(answerInfo);
		}
		
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setAnswerList(tempAnswerList);
		
		return res;
	}
	
	public static AnswerListRes of(Integer statusCode, String message) {
		AnswerListRes res = new AnswerListRes();
		
		res.setStatusCode(statusCode);
		res.setMessage(message);
		
		return res;
	}
}

@Getter
@Setter
class AnswerInfo{
	private String id;
	private String content;
	private Date regDt;
}