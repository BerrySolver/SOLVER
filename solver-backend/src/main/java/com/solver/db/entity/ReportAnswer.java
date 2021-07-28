package com.solver.db.entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportAnswer extends BaseEntity{
	private String reason;
	
	@ManyToOne
	@JoinColumn(name="reporterUserId")
	private User reporterUser;
	
	@ManyToOne
	@JoinColumn(name="answerId")
	private Answer answer;
	
	@ManyToOne
	@JoinColumn(name="reportedUserId")
	private User reportedUser;
}
