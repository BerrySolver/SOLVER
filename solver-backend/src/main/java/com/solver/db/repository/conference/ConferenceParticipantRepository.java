package com.solver.db.repository.conference;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solver.db.entity.conference.ConferenceParticipant;

public interface ConferenceParticipantRepository extends JpaRepository<ConferenceParticipant, String>{

	void deleteByUserId(String id);

}
