package com.solver.api.service;

import javax.servlet.http.HttpServletResponse;

import com.solver.db.entity.question.FavoriteQuestion;
import com.solver.db.entity.question.Question;

public interface FavoriteQuestionService {
	
	FavoriteQuestion createFavoriteQuestion(String accessToken, Question question, HttpServletResponse response);

	void deleteFavoriteQuestion(String token, Question question, HttpServletResponse response);
}
