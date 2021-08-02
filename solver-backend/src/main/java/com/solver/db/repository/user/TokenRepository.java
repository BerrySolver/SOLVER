package com.solver.db.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solver.db.entity.user.Token;

public interface TokenRepository extends JpaRepository<Token, String>{

}
