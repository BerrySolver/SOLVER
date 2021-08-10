package com.solver.api.service;

import javax.servlet.http.HttpServletResponse;

import com.solver.db.entity.question.Question;

public interface BookmarkQuestionService {

	boolean createBookmark(String accessToken, Question question, HttpServletResponse response);

	void deleteBookmark(Question question, String token, HttpServletResponse response);

}
