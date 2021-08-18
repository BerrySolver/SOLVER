package com.solver.api.service;

import javax.servlet.http.HttpServletResponse;

import com.solver.api.request.ConferenceRecordPostReq;
import com.solver.api.request.EvaluationPostReq;

public interface ConferenceService {

	int updateConference(String accessToken, String conferenceId, HttpServletResponse response);

	int goOutConference(String accessToken, String conferenceId, HttpServletResponse response);

	int deleteConference(String accessToken, String conferenceId, HttpServletResponse response);

	int recordConference(String accessToken, String questionId, HttpServletResponse response,
			ConferenceRecordPostReq conferenceRecordPostReq);

	void insertEvaluation(String accessToken, EvaluationPostReq evaluationPostReq, HttpServletResponse response);

}
