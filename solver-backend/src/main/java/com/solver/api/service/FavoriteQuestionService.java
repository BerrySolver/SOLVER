package com.solver.api.service;

import javax.servlet.http.HttpServletResponse;

import com.solver.db.entity.question.FavoriteQuestion;
import com.solver.db.entity.question.Question;

public interface FavoriteQuestionService {
	
	boolean checkFavoriteQuestion(String accessToken, Question question);
	
	FavoriteQuestion createFavoriteQuestion(String token, Question question, HttpServletResponse response);

	void deleteFavoriteQuestion(String token, Question question, HttpServletResponse response);
}
