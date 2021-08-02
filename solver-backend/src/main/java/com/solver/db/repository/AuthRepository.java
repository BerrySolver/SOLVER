package com.solver.db.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solver.api.request.UserLoginPostReq;
import com.solver.db.entity.Auth;
import com.solver.db.entity.User;

public interface AuthRepository extends JpaRepository<Auth, String>{
	Optional<Auth> findByLoginId(String loginId);
	
	Optional<Auth> findByLoginIdAndPassword(String loginId, String password);

	@Transactional
	void deleteByLoginId(String id);
}
