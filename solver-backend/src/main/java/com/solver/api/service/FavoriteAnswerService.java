package com.solver.api.service;

public interface FavoriteAnswerService {

	int createFavoriteAnswer(String accessToken, String answerId);

	int deleteFavoriteAnswer(String accessToken, String answerId);

}
