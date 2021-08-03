package com.solver.api.response;

import java.util.ArrayList;
import java.util.Date;
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
	private List<QuestionForm> questionFormList;
	
	public static QuestionListRes of(Integer statusCode, String message, List<Question> questionList) {
		QuestionListRes res = new QuestionListRes();
		List<QuestionForm> questionFormList = new ArrayList<QuestionForm>();
		
		for (int i = 0; i < questionList.size(); i++) {
			Question question = questionList.get(i);
			QuestionForm qForm = new QuestionForm();
			qForm.setTitle(question.getTitle());
			qForm.setDifficulty(question.getDifficulty());
			qForm.setContent(question.getContent());
			qForm.setNickname(question.getUser().getNickname());
			qForm.setRegDt(question.getRegDt());
			qForm.setAnswerCount(question.getAnswer().size());
			qForm.setLikeCount(question.getFavoriteQuestion().size());
			qForm.setBookMarkCount(question.getBookmarkQuestion().size());
			questionFormList.add(qForm);
		}
		
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setQuestionFormList(questionFormList);
		
		return res;
	}
}

@Getter
@Setter
class QuestionForm{
	private String title;
	private int difficulty;
	private String content;
	private String nickname;
	private Date regDt;
	private int answerCount;
	private int likeCount;
	private int bookMarkCount;
}