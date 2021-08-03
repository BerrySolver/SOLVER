package com.solver.db.repository.conference;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solver.db.entity.conference.ConferenceLog;

public interface ConferenceLogRepository extends JpaRepository<ConferenceLog, String>{
	
	List<ConferenceLog> findByUserIdOrderByRegDt(String userId);

	List<ConferenceLog> findByUserIdOrderByConferenceId(String userId);
}
