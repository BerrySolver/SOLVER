package com.solver.db.entity;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PaidSolver {
	@Id
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	private Date startTime;
	private Date endTime;
}
