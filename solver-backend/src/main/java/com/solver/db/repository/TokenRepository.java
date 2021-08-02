package com.solver.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solver.db.entity.Token;

public interface TokenRepository extends JpaRepository<Token, String>{

}
