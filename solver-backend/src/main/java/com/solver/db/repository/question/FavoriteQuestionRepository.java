package com.solver.db.repository.question;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solver.db.entity.question.FavoriteQuestion;

public interface FavoriteQuestionRepository extends JpaRepository<FavoriteQuestion, String>{
	
	Optional<FavoriteQuestion> findByUserIdAndQuestionId(String userId, String questionId);

	List<FavoriteQuestion> findByQuestionId(String questionId);
}
