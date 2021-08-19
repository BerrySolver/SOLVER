package com.solver.api.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import com.solver.api.request.AnswerCreatePostReq;
import com.solver.api.request.AnswerUpdatePatchReq;
import com.solver.db.entity.answer.Answer;
import com.solver.db.entity.question.Question;

public interface AnswerService {

	void createAnswer(String accessToken, AnswerCreatePostReq answerCreateGetReq, String questionId, HttpServletResponse response);

	boolean deleteAnswer(String accessToken, String answerId, HttpServletResponse response);

	boolean updateAnswer(String accessToken, String answerId, AnswerUpdatePatchReq answerUpdatePatchReq, HttpServletResponse response);

	List<Answer> getAnswerList(String questionId);

	Optional<Answer> getById(String answerId);

	Answer selectAnswer(String token, Question question, Answer answer);
	
}
