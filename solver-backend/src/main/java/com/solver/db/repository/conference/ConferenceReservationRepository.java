package com.solver.db.repository.conference;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solver.db.entity.conference.ConferenceReservation;

public interface ConferenceReservationRepository extends JpaRepository<ConferenceReservation, String>{

	Optional<ConferenceReservation> findByUserIdAndQuestionId(String userId, String questionId);

}
