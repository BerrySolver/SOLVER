package com.solver.api.service;

import com.solver.db.entity.conference.ConferenceReservation;
import com.solver.db.entity.question.Question;

public interface ConferenceReservationService {

	ConferenceReservation createConferenceReservation(String token, Question question);

	void deleteConferenceReservation(String token, Question question);

}
