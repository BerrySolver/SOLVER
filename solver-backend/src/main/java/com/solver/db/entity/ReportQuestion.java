package com.solver.db.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ReportQuestion extends BaseEntity{
	private String reason;
	
	@ManyToOne
	@JoinColumn(name="reporterUserId")
	private User reporterUser;
	
	@ManyToOne
	@JoinColumn(name="questionId")
	private Question question;
}
