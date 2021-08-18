package com.solver.api.service;

import javax.servlet.http.HttpServletResponse;

import com.solver.api.request.ConferenceLogPostReq;

public interface ConferenceLogService {
	int createConferenceLog(String accessToken, HttpServletResponse response, ConferenceLogPostReq conferenceLogPostReq);

}
