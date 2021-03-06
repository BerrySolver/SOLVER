package com.solver.api.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import com.solver.api.request.QuestionGetListReq;
import com.solver.api.request.QuestionPatchReq;
import com.solver.api.request.QuestionPostReq;
import com.solver.api.response.QuestionListRes;
import com.solver.api.response.QuestionRecommendRes;
import com.solver.api.response.QuestionRes;
import com.solver.db.entity.question.Question;

public interface QuestionService {
	Question createQuestion(QuestionPostReq questionPostReq, String token, HttpServletResponse response);

	Optional<Question> getById(String questionId);

	Question updateQuestion(QuestionPatchReq questionPatchReq, Question question, String token, HttpServletResponse response);

	void deleteQuestion(Question question, String token, HttpServletResponse response);

	QuestionListRes getQuestionList(QuestionGetListReq questionGetListReq);

	List<Question> getMyQuestionList(String token, HttpServletResponse response);

	List<QuestionRecommendRes> getRecommendQuestion(String accessToken, HttpServletResponse response);
}	
