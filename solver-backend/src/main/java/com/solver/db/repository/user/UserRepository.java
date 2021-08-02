package com.solver.db.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solver.db.entity.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	Optional<User> findByNickname(String nickname);

	Optional<User> findByKakaoId(Long kakaoId);
	
	void deleteByNickname(String nickname);
}
