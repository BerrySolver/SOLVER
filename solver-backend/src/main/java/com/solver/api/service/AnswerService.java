package com.solver.api.service;

import com.solver.api.request.AnswerCreateGetReq;

public interface AnswerService {

	void createAnswer(String accessToken, AnswerCreateGetReq answerCreateGetReq, String questionId);

}
