package com.solver.api.service;

import com.solver.db.entity.question.Question;

public interface BookmarkQuestionService {

	boolean createBookmark(String accessToken, Question question);

}
