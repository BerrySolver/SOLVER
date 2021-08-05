package com.solver.db.entity.question;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.solver.db.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Hashtag extends BaseEntity{
	private String tagName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="questionId")
	private Question question;
}
