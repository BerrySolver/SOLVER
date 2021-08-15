package com.solver.db.repository.code;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solver.db.entity.code.PointCode;
import com.solver.db.entity.user.PointLog;

public interface PointCodeRepository extends JpaRepository<PointCode, String>{

	PointCode findByPointCode(String code);
}
