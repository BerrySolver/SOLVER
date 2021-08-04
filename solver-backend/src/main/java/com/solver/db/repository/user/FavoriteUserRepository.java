package com.solver.db.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solver.db.entity.user.FavoriteUser;
import com.solver.db.entity.user.User;

public interface FavoriteUserRepository extends JpaRepository<FavoriteUser, String>{

	Optional<User> findByUserIdAndFollowingUserId(String userId, String followingUserId);

}
