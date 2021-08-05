package com.solver.api.response;

import java.util.ArrayList;
import java.util.List;

import com.solver.common.model.BaseResponse;
import com.solver.db.entity.question.Question;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

// 내 질문 조회 시 반환되는 폼
@Getter
@Setter
@ApiModel("QuestionMeResponse")
public class QuestionMeRes extends BaseResponse{
	private List<QuestionMeForm> myQuestionList;
	
	public static QuestionMeRes of(Integer statusCode, String message, List<Question> questionList) {
		QuestionMeRes res = new QuestionMeRes();
		List<QuestionMeForm> myQuestionList = new ArrayList<QuestionMeForm>();
		
		for (int i = 0; i < questionList.size(); i++) {
			Question quetion = questionList.get(i);
			QuestionMeForm qForm = new QuestionMeForm();
			qForm.setId(quetion.getId());
			qForm.setTitle(quetion.getTitle());
			qForm.setAnswerCount(quetion.getAnswer().size());
			myQuestionList.add(qForm);
		}
		
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setMyQuestionList(myQuestionList);
		
		return res;
	}

}

//내 질문 목록에서 각 질문들이 갖고 있는 내용
@Getter
@Setter
class QuestionMeForm{
	// 질문 Id
	private String Id;
	// 제목
	private String title;
	// 답변 개수
	private int answerCount;
}