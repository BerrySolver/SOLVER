package com.solver.api.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.solver.common.model.BaseResponse;
import com.solver.db.entity.answer.Answer;
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
	
	private HashMap<String, Integer> answerStatistics = new HashMap<String, Integer>();
	
	private int textAnswerCount;
	
	private int videoAnswerCount;
	
	private float videoAnswerTime;
	
	public static ProfileTabRes makeAnswerStatistics(
			int videoAnswerTime, int videoAnswerCount, int textAnswerCount, List<Answer> myAnswerList) {
		ProfileTabRes res = new ProfileTabRes();
		HashMap<String, Integer> answerStatistics = new HashMap<String, Integer>();
		
		for (Answer answer : myAnswerList) {
			String subCategoryName = answer.getQuestion().getSubCategory().getSubCategoryName();
			if (answerStatistics.containsKey(subCategoryName)) {
				answerStatistics.put(subCategoryName, answerStatistics.get(subCategoryName) + 1);
			} else {
				answerStatistics.put(subCategoryName, 1);
			}
		}
		
		res.setTextAnswerCount(textAnswerCount);
		res.setVideoAnswerCount(videoAnswerCount);
		res.setVideoAnswerTime(videoAnswerTime);
		res.setAnswerStatistics(answerStatistics);
		
		return res;
	}
	
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