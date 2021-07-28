package com.solver.db.entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportComment extends BaseEntity{
	private String reason;
	
	@ManyToOne
	@JoinColumn(name="reporterUserId")
	private User reporterUser;
	
	@ManyToOne
	@JoinColumn(name="commentId")
	private Comment comment;
	
	@ManyToOne
	@JoinColumn(name="reportedUserId")
	private User reportedUser;
}
