package com.solver.db.repository.conference;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solver.db.entity.conference.Conference;

public interface ConferenceRepository extends JpaRepository<Conference, String>{

	Optional<Conference> findByIdAndQuestionUserId(String id, String id2);

}
