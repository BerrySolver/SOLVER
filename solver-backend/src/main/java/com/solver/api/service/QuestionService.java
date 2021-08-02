package com.solver.api.service;

import java.util.Optional;

import com.solver.api.request.QuestionPatchReq;
import com.solver.api.request.QuestionPostReq;
import com.solver.db.entity.question.Question;

public interface QuestionService {
	Question createQuestion(QuestionPostReq questionPostReq);

	Optional<Question> getById(String questionId);

	Question updateQuestion(QuestionPatchReq questionPatchReq, Question question);

	void deleteQuestion(Question question);
}	
