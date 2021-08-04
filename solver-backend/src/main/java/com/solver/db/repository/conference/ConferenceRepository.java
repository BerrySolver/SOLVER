package com.solver.db.repository.conference;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solver.db.entity.conference.Conference;

public interface ConferenceRepository extends JpaRepository<Conference, String>{

}
