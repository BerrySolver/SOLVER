package com.solver.db.repository.answer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solver.db.entity.answer.FavoriteAnswer;

public interface FavoriteAnswerRepository extends JpaRepository<FavoriteAnswer, String>{

	Optional<FavoriteAnswer> findByUserIdAndAnswerId(String userId, String answerId);
}
