package com.solver.db.repository.code;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.solver.db.entity.code.FavoriteField;

public interface FavoriteFieldRepository extends JpaRepository<FavoriteField, String> {

	List<FavoriteField> findByUserId(String userId);

	@Transactional
	@Modifying
	void deleteByUserId(String userId);
}
