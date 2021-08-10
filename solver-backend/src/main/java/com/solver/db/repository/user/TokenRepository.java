package com.solver.db.repository.user;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.solver.db.entity.user.Token;
import com.solver.db.entity.user.User;

public interface TokenRepository extends JpaRepository<Token, User>{
	@Transactional
	@Modifying
	void deleteByUserId(String id);

	Token findByAccessToken(String accessToken);

}
