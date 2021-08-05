package com.solver.api.service;

import java.util.Optional;

import com.solver.api.request.QuestionPatchReq;
import com.solver.api.request.QuestionPostReq;
import com.solver.db.entity.question.Question;

public interface QuestionService {
	Question createQuestion(QuestionPostReq questionPostReq, String token);

	Optional<Question> getById(String questionId);

	Question updateQuestion(QuestionPatchReq questionPatchReq, Question question, String token);

	void deleteQuestion(Question question, String token);

	List<Question> getQuestionList(QuestionGetListReq questionGetListReq);

	List<Question> getMyQuestionList(String token);
}	
