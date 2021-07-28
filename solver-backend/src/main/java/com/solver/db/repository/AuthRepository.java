package com.solver.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solver.db.entity.Auth;

public interface AuthRepository extends JpaRepository<Auth, String>{

}
