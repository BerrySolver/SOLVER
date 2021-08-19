package com.solver.db.entity.user;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.solver.db.entity.BaseEntity;
import com.solver.db.entity.code.Code;
import com.solver.db.entity.question.Question;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Notification extends BaseEntity{	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="type")
	private Code code;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="questionId")
	private Question question;
	
	private Date regDt;
}
