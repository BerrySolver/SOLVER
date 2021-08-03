package com.solver.api.response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.solver.common.model.BaseResponse;
import com.solver.db.entity.question.Question;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

//질문 전체 조회 시 반환되는 폼
@Getter
@Setter
@ApiModel("QuestionListResponse")
public class QuestionListRes extends BaseResponse{
	// 전체 조회 시에는 사용자에게 보여줄 필요한 정보들을 새로 가공해야 한다. 따라서, 새로운 클래스를 만들고 그 객체의 리스트를 반환한다.
	private List<QuestionForm> questionFormList;
	
	public static QuestionListRes of(Integer statusCode, String message, List<Question> questionList) {
		QuestionListRes res = new QuestionListRes();
		// 기본 question 형태의 자료들을 전부 qustionForm 형태로 바꿔서 새로운 questionForm 리스트에 담아 결과로 반환할 것이다.
		List<QuestionForm> questionFormList = new ArrayList<QuestionForm>();
		
		// question 리스트에서 하나씩 가져와 questionForm으로 재가공
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

//전체 목록에서 각 질문들이 갖고 있는 내용
@Getter
@Setter
class QuestionForm{
	// 제목
	private String title;
	// 난이도
	private int difficulty;
	// 내용
	private String content;
	// 작성자 닉네임
	private String nickname;
	// 생성 시간
	private Date regDt;
	// 답변 개수
	private int answerCount;
	// 좋아요 개수
	private int likeCount;
	// 북마크 개수
	private int bookMarkCount;
}