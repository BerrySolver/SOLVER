package com.solver.db.entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteAnswer extends BaseEntity{
	@ManyToOne
	@JoinColumn(name="userId")
	User user;
	
	@ManyToOne
	@JoinColumn(name="questionId")
	Question question;
}
