package com.solver.db.entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Hashtag extends BaseEntity{
	private String tagName;
	
	@ManyToOne
	@JoinColumn(name="questionId")
	private Question question;
}
