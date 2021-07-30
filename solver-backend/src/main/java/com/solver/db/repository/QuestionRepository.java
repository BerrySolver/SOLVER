package com.solver.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solver.db.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, String>{
	
}
