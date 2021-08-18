package com.solver.db.repository.user;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.solver.db.entity.user.PaidSolver;
import com.solver.db.entity.user.PointLog;

public interface PaidSolverRepository extends JpaRepository<PaidSolver, String> {
	
	List<PaidSolver> findByStartTimeBeforeAndEndTimeAfter(Date nowDate1, Date nowDate2);
}
