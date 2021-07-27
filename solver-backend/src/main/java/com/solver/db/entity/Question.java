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
public class Question extends BaseEntity{
	private String title;
	private String content;
	private String mainCategroy;
	private String subCategory;
	private int difficulty;
	private String type;
	private Date regDt;
	private Date expirationTime;
	private boolean conferenceOpened;
	private int readCount;
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	@OneToMany(mappedBy="question", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<FavoriteUser> favoriteUser;
	
	@OneToMany(mappedBy="question", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<FavoriteQuestion> favoriteQuestion;
	
	@OneToMany(mappedBy="question", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<BookmarkQuestion> bookmarkQuestion;
	
	@OneToMany(mappedBy="question", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Hashtag> hashtag;
	
	@OneToMany(mappedBy="question", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<ReportQuestion> reportQuestion;
	
	@OneToMany(mappedBy="question", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Answer> answer;
	
	@OneToMany(mappedBy="question", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<FavoriteAnswer> favoriteAnswer;
}
