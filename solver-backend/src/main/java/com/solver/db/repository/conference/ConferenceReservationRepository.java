package com.solver.db.repository.conference;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.solver.db.entity.answer.Answer;
import com.solver.db.entity.conference.ConferenceReservation;

public interface ConferenceReservationRepository extends JpaRepository<ConferenceReservation, String>{

	Optional<ConferenceReservation> findByUserIdAndQuestionId(String userId, String questionId);
	
	@Query(value = "select * from answer as a where a.user_id= :userId and a.type in (050 , 051)", nativeQuery = true)
	List<Answer> findTextAnswerByUserId(@Param("userId") String userId);
	
	@Query(value = "select * from conference_reservation as a where a.user_id= :userId and a.start_dt between :startDt and :endDt", nativeQuery = true)
	List<ConferenceReservation> findAllByUserId(@Param("userId") String userId, @Param("startDt") Date startDt, @Param("endDt") Date endDt);

}
