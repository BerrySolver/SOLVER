package com.solver.db.entity.answer;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.solver.db.entity.BaseEntity;
import com.solver.db.entity.code.Code;
import com.solver.db.entity.comment.Comment;
import com.solver.db.entity.question.Question;
import com.solver.db.entity.user.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Answer extends BaseEntity{
	private String content;
	private Date regDt;
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="questionId")
	private Question question;
	
	@ManyToOne
	@JoinColumn(name="type")
	private Code code;
	
	@OneToMany(mappedBy="answer", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<FavoriteAnswer> favoriteAnswer;
	
	@OneToMany(mappedBy="answer", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<ReportAnswer> reportAnswer;
	
	@OneToMany(mappedBy="answer", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<Evaluation> evaluation;
	
	@OneToMany(mappedBy="answer", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<Comment> comment;
}
