package com.solver.api.service;

import com.solver.api.request.AnswerCreateGetReq;
import com.solver.api.request.AnswerUpdatePatchReq;

public interface AnswerService {

	void createAnswer(String accessToken, AnswerCreateGetReq answerCreateGetReq, String questionId);

	boolean deleteAnswer(String accessToken, String answerId);

	boolean updateAnswer(String accessToken, String answerId, AnswerUpdatePatchReq answerUpdatePatchReq);
	
}
