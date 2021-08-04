package com.solver.api.service;

public interface ConferenceService {

	int updateConference(String accessToken, String conferenceId);

	int goOutConference(String accessToken, String conferenceId);

}
