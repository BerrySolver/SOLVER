package com.solver.db.entity.question;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.solver.db.entity.BaseEntity;
import com.solver.db.entity.answer.Answer;
import com.solver.db.entity.code.Category;
import com.solver.db.entity.code.Code;
import com.solver.db.entity.conference.Conference;
import com.solver.db.entity.conference.ConferenceReservation;
import com.solver.db.entity.user.FavoriteUser;
import com.solver.db.entity.user.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Question extends BaseEntity{
	private String title;
	private String content;
	private int difficulty;
	private Date regDt;
	private Date expirationTime;
	private boolean conferenceOpened;
	private int readCount;
	
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
	@JoinColumn(name="mainCategory")
	private Code mainCategory;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="subCategory")
	private Category subCategory;
	
	@OneToMany(mappedBy="question", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<FavoriteUser> favoriteUser;
	
	@OneToMany(mappedBy="question", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<FavoriteQuestion> favoriteQuestion;
	
	@OneToMany(mappedBy="question", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<BookmarkQuestion> bookmarkQuestion;
	
	@OneToMany(mappedBy="question", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<Hashtag> hashtag;
	
	@OneToMany(mappedBy="question", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<ReportQuestion> reportQuestion;
	
	@OneToMany(mappedBy="question", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<Answer> answer;
	
	@OneToMany(mappedBy="question", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<Conference> conference;
	
	@OneToMany(mappedBy="question", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<ConferenceReservation> conferenceReservation;
}
