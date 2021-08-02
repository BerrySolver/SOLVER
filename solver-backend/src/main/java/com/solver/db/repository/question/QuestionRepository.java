package com.solver.db.repository.question;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solver.db.entity.question.Question;

public interface QuestionRepository extends JpaRepository<Question, String>{
	
}
