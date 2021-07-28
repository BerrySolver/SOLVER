package com.solver.db.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Answer extends BaseEntity{
	private String text;
	private String type;
	private Date regDt;
	
	@ManyToOne
	@JoinColumn(name="userId")
	User user;
	
	@ManyToOne
	@JoinColumn(name="questionId")
	Question question;
	
	@OneToMany(mappedBy="answerId", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<ReportAnswer> reportAnswer;
	
	@OneToMany(mappedBy="answerId", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Comment> comment;
}
