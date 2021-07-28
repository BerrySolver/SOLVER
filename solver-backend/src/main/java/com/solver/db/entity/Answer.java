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
	private Date regDt;
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="questionId")
	private Question question;
	
	@ManyToOne
	@JoinColumn(name="type")
	private Code code;
	
	@OneToMany(mappedBy="answer", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<FavoriteAnswer> favoriteAnswer;
	
	@OneToMany(mappedBy="answer", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<ReportAnswer> reportAnswer;
	
	@OneToMany(mappedBy="answer", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Evaluation> evaluation;
	
	@OneToMany(mappedBy="answer", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Comment> comment;
}
