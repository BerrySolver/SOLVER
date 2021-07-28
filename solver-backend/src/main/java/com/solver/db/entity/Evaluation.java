package com.solver.db.entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Evaluation extends BaseEntity{
	private int score;
	private String reason;
	
	@ManyToOne
	@JoinColumn(name="answerUserId")
	private User answerUser;
	
	@ManyToOne
	@JoinColumn(name="answerId")
	private Answer answer;
	
	@ManyToOne
	@JoinColumn(name="questionUserId")
	private User questionUser;
	
}
