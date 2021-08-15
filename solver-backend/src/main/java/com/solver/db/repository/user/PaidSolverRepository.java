package com.solver.db.repository.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solver.db.entity.user.PaidSolver;
import com.solver.db.entity.user.PointLog;

public interface PaidSolverRepository extends JpaRepository<PaidSolver, String> {
	
}
