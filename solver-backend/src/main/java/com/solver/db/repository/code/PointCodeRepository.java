package com.solver.db.repository.code;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solver.db.entity.code.PointCode;

public interface PointCodeRepository extends JpaRepository<PointCode, String>{

	PointCode findByCode(String code);

}
