package com.solver.db.repository.answer;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.solver.db.entity.answer.Answer;

public interface AnswerRepository extends JpaRepository<Answer, String>{

	List<Answer> findByUserId(String userId);
	
	@Query(value = "select * from answer as a where a.user_id= :userId and a.type in (050 , 052)", nativeQuery = true)
	List<Answer> findTextAnswerByUserId(@Param("userId") String userId);
	
	@Query(value = "select * from answer as a where a.user_id= :userId and a.type in (051 , 053)", nativeQuery = true)
	List<Answer> findVideoAnswerByUserId(@Param("userId") String userId);

	List<Answer> findByQuestionIdOrderByRegDtAsc(String questionId);

	List<Answer> findByRegDtBetween(Date todayStart, Date todayEnd);

	List<Answer> findByQuestionId(String questionId);

}
