package com.solver.api.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.solver.db.entity.conference.ConferenceReservation;
import com.solver.db.entity.question.Question;

public interface ConferenceReservationService {

	ConferenceReservation createConferenceReservation(String token, Question question, HttpServletResponse response);

	void deleteConferenceReservation(String token, Question question, HttpServletResponse response);
	
	List<ConferenceReservation> getReservationList(String accessToken, HttpServletResponse response);

}
