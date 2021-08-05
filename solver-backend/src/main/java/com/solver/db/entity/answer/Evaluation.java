package com.solver.db.entity.answer;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.solver.db.entity.BaseEntity;
import com.solver.db.entity.user.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Evaluation extends BaseEntity{
	private int score;
	private String reason;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="answerUserId")
	private User answerUser;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="answerId")
	private Answer answer;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="questionUserId")
	private User questionUser;
	
}
