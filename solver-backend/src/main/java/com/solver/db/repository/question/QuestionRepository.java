package com.solver.db.repository.question;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solver.db.entity.question.Question;

public interface QuestionRepository extends JpaRepository<Question, String>{

	List<Question> findByUserId(String userId);
	
}
