package com.solver.db.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.LastModifiedBy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User extends BaseEntity{
	private String nickname;
	private String introduction;
	private String linkText;
	private String profileUrl;
	
	@ManyToOne
	@JoinColumn(name="type")
	private Code code;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<FavoriteField> favoriteField;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<FavoriteUser> favoriteUser;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<UserCalendar> userCalendar;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Notification> notification;
	
	@OneToMany(mappedBy="sendUser", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Message> sendMessage;
	
	@OneToMany(mappedBy="receiveUser", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Message> receiveMessage;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<PaidSolver> paidSolver;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<PointLog> pointLog;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Question> question;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<FavoriteQuestion> favoriteQuestion;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<BookmarkQuestion> bookmarkQuestion;
	
	@OneToMany(mappedBy="reporterUser", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<ReportQuestion> reportQuestion;
	
	@OneToMany(mappedBy="reportedUser", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<ReportQuestion> reportedQuestion;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Answer> answer;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<FavoriteAnswer> favoriteAnswer;
	
	@OneToMany(mappedBy="reporterUser", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<ReportAnswer> reportAnswer;
	
	@OneToMany(mappedBy="reportedUser", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<ReportAnswer> reportedAnswer;
	
	@OneToMany(mappedBy="answerUser", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Evaluation> evaluateAnswer;
	
	@OneToMany(mappedBy="questionUser", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Evaluation> evaluatedAnswer;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Comment> comment;
	
	@OneToMany(mappedBy="reporterUser", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<ReportComment> reportComment;
	
	@OneToMany(mappedBy="reportedUser", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<ReportComment> reportedComment;
	
	@OneToMany(mappedBy="questionUser", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Conference> questionConference;
	
	@OneToMany(mappedBy="answerUser", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Conference> answerConference;
}
