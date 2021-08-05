package com.solver.api.response;

import java.util.ArrayList;
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
	private List<ProfileQuestionForm> answerQuestionList;
	
	private List<ProfileQuestionForm> myQuestionList;
	
	private List<ProfileQuestionForm> bookmarkQuestionList;
	
	private int textAnswerCount;
	
	private int videoAnswerCount;
	
	private float videoAnswerTime;
	
	public static ProfileTabRes makeAnswerQuestionList(List<Question> questionList) {
		ProfileTabRes res = new ProfileTabRes();
		
		List<ProfileQuestionForm> questionFormList = new ArrayList<ProfileQuestionForm>();
		
		for (Question question : questionList) {
			ProfileQuestionForm qForm = new ProfileQuestionForm();
			qForm.setId(question.getId());
			qForm.setTitle(question.getTitle());
			qForm.setAnswerCount(question.getAnswer().size());
			questionFormList.add(qForm);
		}
		
		res.setAnswerQuestionList(questionFormList);
		
		return res;
	}
	
	public static ProfileTabRes makeMyQuestionList(List<Question> questionList) {
		ProfileTabRes res = new ProfileTabRes();
		
		List<ProfileQuestionForm> questionFormList = new ArrayList<ProfileQuestionForm>();
		
		for (Question question : questionList) {
			ProfileQuestionForm qForm = new ProfileQuestionForm();
			qForm.setId(question.getId());
			qForm.setTitle(question.getTitle());
			qForm.setAnswerCount(question.getAnswer().size());
			questionFormList.add(qForm);
		}
		
		res.setMyQuestionList(questionFormList);
		
		return res;
	}
	
	public static ProfileTabRes makeBookmarkQuestionList(List<Question> questionList) {
		ProfileTabRes res = new ProfileTabRes();
		
		List<ProfileQuestionForm> questionFormList = new ArrayList<ProfileQuestionForm>();
		
		for (Question question : questionList) {
			ProfileQuestionForm qForm = new ProfileQuestionForm();
			qForm.setId(question.getId());
			qForm.setTitle(question.getTitle());
			qForm.setAnswerCount(question.getAnswer().size());
			questionFormList.add(qForm);
		}
		
		res.setBookmarkQuestionList(questionFormList);
		
		return res;
	}
}

@Getter
@Setter
class ProfileQuestionForm{
	// 질문 Id
	private String Id;
	// 제목
	private String title;
	// 답변 개수
	private int answerCount;
}