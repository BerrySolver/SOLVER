package com.solver.api.service;

import javax.servlet.http.HttpServletResponse;

public interface FavoriteAnswerService {

	int createFavoriteAnswer(String accessToken, String answerId, HttpServletResponse response);

	int deleteFavoriteAnswer(String accessToken, String answerId, HttpServletResponse response);

}
