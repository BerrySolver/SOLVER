package com.solver.db.entity;

import java.util.Date;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Conference extends BaseEntity{
	private Date regDt;
	private int maxCount;
	private int count;
	
	@ManyToOne
	@JoinColumn(name="questionId")
	private Question question;
	
	@ManyToOne
	@JoinColumn(name="questionUserId")
	private User questionUser;
	
	@ManyToOne
	@JoinColumn(name="answerUserId")
	private User answerUser;
	
	@ManyToOne
	@JoinColumn(name="type")
	private Code code;
}
