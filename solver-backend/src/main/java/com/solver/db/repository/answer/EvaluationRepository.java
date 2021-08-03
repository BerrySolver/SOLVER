package com.solver.db.repository.answer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solver.db.entity.answer.Evaluation;

public interface EvaluationRepository extends JpaRepository<Evaluation, String>{

	List<Evaluation> findByAnswerUserId(String userId);

}
