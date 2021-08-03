package com.solver.api.service;

import java.util.Optional;

import com.solver.api.request.QuestionPatchReq;
import com.solver.api.request.QuestionPostReq;
import com.solver.db.entity.question.Question;
import com.solver.db.entity.user.User;

public interface QuestionService {
	Question createQuestion(QuestionPostReq questionPostReq, String token);

	Optional<Question> getById(String questionId);

	Question updateQuestion(QuestionPatchReq questionPatchReq, Question question, String token);

	void deleteQuestion(Question question, String token);
}	
