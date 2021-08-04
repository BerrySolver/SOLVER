package com.solver.api.service;

import com.solver.db.entity.question.FavoriteQuestion;
import com.solver.db.entity.question.Question;

public interface FavoriteQuestionService {
	
	FavoriteQuestion createFavoriteQuestion(String accessToken, Question question);

	void deleteFavoriteQuestion(String token, Question question);
}
